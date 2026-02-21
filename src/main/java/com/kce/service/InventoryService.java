package com.kce.service;

import java.util.Date;
import java.util.List;

import com.kce.bean.DamageReport;
import com.kce.bean.Item;
import com.kce.dao.DamageReportDAO;
import com.kce.dao.ItemDAO;
import com.kce.util.ActiveDamageReportsException;
import com.kce.util.InsufficientStockException;
import com.kce.util.ValidationException;

public class InventoryService {

    private ItemDAO itemDAO = new ItemDAO();
    private DamageReportDAO damageDAO = new DamageReportDAO();

    // 🔹 View single item
    public Item viewItemDetails(String itemCode) {

        if(itemCode == null || itemCode.trim().isEmpty())
            return null;

        return itemDAO.findItem(itemCode);
    }

    // 🔹 View all items
    public List<Item> viewAllItems() {
        return itemDAO.viewAllItems();
    }

    // 🔹 Add new item
    public boolean addNewItem(Item item)
            throws ValidationException {

        if(item == null ||
           item.getItemCode() == null || item.getItemCode().trim().isEmpty() ||
           item.getItemDescription() == null || item.getItemDescription().trim().isEmpty() ||
           item.getUnitOfMeasure() == null || item.getUnitOfMeasure().trim().isEmpty() ||
           item.getUnitPrice() <= 0 ||
           item.getOnHandQuantity() < 0)
        {
            throw new ValidationException();
        }

        Item existing =
                itemDAO.findItem(item.getItemCode());

        if(existing != null)
            throw new ValidationException(); // already exists

        item.setTotalDamagedQuantity(0);

        return itemDAO.insertItem(item);
    }

    // 🔹 Remove item
    public boolean removeItem(String itemCode)
            throws ValidationException,
                   ActiveDamageReportsException {

        if(itemCode == null || itemCode.trim().isEmpty())
            throw new ValidationException();

        List<DamageReport> activeReports =
                damageDAO.findActiveDamageReportsByItem(itemCode);

        if(activeReports != null &&
           activeReports.size() > 0)
        {
            throw new ActiveDamageReportsException();
        }

        return itemDAO.deleteItem(itemCode);
    }

    // 🔹 Log Damage
    public boolean logDamage(String itemCode,
                             Date reportDate,
                             String reportedBy,
                             String reason,
                             double damagedQuantity)
            throws ValidationException,
                   InsufficientStockException {

        if(itemCode == null || itemCode.trim().isEmpty() ||
           reportedBy == null || reportedBy.trim().isEmpty() ||
           reportDate == null ||
           damagedQuantity <= 0)
        {
            throw new ValidationException();
        }

        Item item =
                itemDAO.findItem(itemCode);

        if(item == null)
            return false;

        if(damagedQuantity >
           item.getOnHandQuantity())
        {
            throw new InsufficientStockException();
        }

        // Create damage report (ID auto generated)
        DamageReport report =
                new DamageReport();

        report.setItemCode(itemCode); // if not using @ManyToOne
        report.setReportDate(reportDate);
        report.setReportedBy(reportedBy);
        report.setReason(reason);
        report.setDamagedQuantity(damagedQuantity);
        report.setStatus("OPEN");

        boolean damageSaved =
                damageDAO.recordDamageReport(report);

        double newOnHand =
                item.getOnHandQuantity() - damagedQuantity;

        double newTotalDamaged =
                item.getTotalDamagedQuantity() + damagedQuantity;

        boolean itemUpdated =
                itemDAO.updateQuantities(
                        itemCode,
                        newOnHand,
                        newTotalDamaged
                );

        return damageSaved && itemUpdated;
    }

    // 🔹 Resolve Damage
    public boolean resolveDamageReport(int damageID,
                                       String resolutionAction)
            throws ValidationException {

        if(damageID <= 0)
            throw new ValidationException();

        if(resolutionAction == null ||
           resolutionAction.trim().isEmpty())
            throw new ValidationException();

        DamageReport report =
                damageDAO.findDamageReportsByID(damageID);

        if(report == null)
            return false;

        if(report.getStatus().equalsIgnoreCase("REVERSED") ||
           report.getStatus().equalsIgnoreCase("CONFIRMED"))
            return false;

        Item item =
                itemDAO.findItem(report.getItemCode());

        if(item == null)
            return false;

        if(resolutionAction.equalsIgnoreCase("REVERSE"))
        {
            double newOnHand =
                    item.getOnHandQuantity() +
                    report.getDamagedQuantity();

            double newTotalDamaged =
                    item.getTotalDamagedQuantity() -
                    report.getDamagedQuantity();

            itemDAO.updateQuantities(
                    report.getItemCode(),
                    newOnHand,
                    newTotalDamaged
            );

            damageDAO.updateDamageStatus(
                    damageID,
                    "REVERSED"
            );
        }
        else if(resolutionAction.equalsIgnoreCase("CONFIRMED"))
        {
            damageDAO.updateDamageStatus(
                    damageID,
                    "CONFIRMED"
            );
        }
        else
        {
            throw new ValidationException();
        }

        return true;
    }
}
