package pos.machine;

import java.util.*;

public class PosMachine {
    public static void main(String[] args) {
        PosMachine posMachine = new PosMachine();
        System.out.println(posMachine.printReceipt(ItemDataLoader.loadBarcodes()));

    public String printReceipt (List<String> barcodes) {
        List<ReceiptItem> receiptItems = convertToItems(barcodes);
        Receipt receipt = calculateReceipt(receiptItems);
        return spliceReceipt(receipt);
    }

    private List<ReceiptItem> convertToItems (List <String> barcodes)
        LinkedList<ReceiptItem> itemsWithDetail = new LinkedList<ReceiptItem>(); //itemsWithDetail [a,b,c,d,e]
        List<ItemInfo> itemsInfo = loadAllItemsInfo(); //itemsInfo [a,b,c,d,e]
        barcodes = new ArrayList<>(new LinkedHashSet<>(barcodes)); //distinct barcode
        for (String barcode : barcodes) {
            ReceiptItem receiptItemDetail = new ReceiptItem();
            for (ItemInfo itemInfoVal : itemsInfo) {
                if (barcode.equals(itemInfoVal.getBarcode())) {
                    receiptItemDetail.setName(itemInfoVal.getName());
                    receiptItemDetail.setUnitPrice(itemInfoVal.getPrice());
                    receiptItemDetail.setQuantity(Collections.frequency(ItemDataLoader.loadBarcodes(), barcode));
                }
            }
            itemsWithDetail.add(receiptItemDetail);
        }
        return itemsWithDetail;
    }

    private Receipt calculateReceipt (List < ReceiptItem > itemsWithDetail) {
        Receipt receipt = new Receipt();
        receipt.setItemList(calculateItemsSubtotal(itemsWithDetail));  //calculateItemsSubtotal(itemsWithDetail)
        receipt.setTotalPrice(calculateTotalPrice(itemsWithDetail)); //24
        return receipt;
    }

    private List<ItemInfo> loadAllItemsInfo () {
        return ItemDataLoader.loadAllItemInfos();
    }

    private List<ReceiptItem> calculateItemsSubtotal (List < ReceiptItem > itemsWithDetail) {
        for (ReceiptItem receiptItemValue : itemsWithDetail) {
            receiptItemValue.setSubTotal(receiptItemValue.getQuantity() * receiptItemValue.getUnitPrice());
        }
        return itemsWithDetail;
    }

    private int calculateTotalPrice (List < ReceiptItem > itemsWithDetail) {
        int totalPrice = 0;
        for (ReceiptItem receiptItemDetail : itemsWithDetail) {
            totalPrice += receiptItemDetail.getSubTotal();
        }
        return totalPrice;
    }


    private String spliceItemDetail (Receipt receipt){
        String itemsDetail = "";
        for (ReceiptItem receiptItemValue : receipt.getItemList()) {
            itemsDetail += "Name: " + receiptItemValue.getName() +
                    ", Quantity: " + receiptItemValue.getQuantity() +
                    ", Unit price: " + receiptItemValue.getUnitPrice() + " (yuan)" +
                    ", Subtotal: " + receiptItemValue.getSubTotal() + " (yuan)\n";
        }
        return itemsDetail;
    }

    private String spliceReceipt (Receipt receipt){
        String itemsDetail = spliceItemDetail(receipt);
        int totalPrice = receipt.getTotalPrice();

        return ("***<store earning no money>Receipt***\n" + itemsDetail + "----------------------\n" +
                "Total: " + totalPrice + " (yuan)\n" +
                "**********************");
        }
    }
}
