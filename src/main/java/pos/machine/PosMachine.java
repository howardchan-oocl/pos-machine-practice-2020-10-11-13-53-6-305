package pos.machine;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PosMachine {
    public static void main(String[] args) {
        List<String> listOfBarcode = ItemDataLoader.loadBarcodes();

        PosMachine posMachine = new PosMachine();
        System.out.println(posMachine.printReceipt(listOfBarcode));
    }

    /*
    P:3min
    D:3min
    C:all good
    A:no need
    */
    public String printReceipt(List<String> barcodes) {
        Map<String, Long> barcodeCountMap = countBarcode(barcodes);

        String receipt = "***<store earning no money>Receipt***\n";
        int total = 0;

        for (String barcode : barcodeCountMap.keySet()) {
            receipt += generateSubStringForItem(barcode, barcodeCountMap.get(barcode));
            total += calculateSubTotal(barcode, barcodeCountMap.get(barcode));
        }

        receipt += "----------------------\nTotal: " + total + " (yuan)\n**********************";
        return receipt;
    }

    /*
    P:5min
    D:5min
    C:doesnt know the method putIfAbsent
    A:practice more
    */
    private LinkedHashMap<String, Long> countBarcode(List<String> barcodes) {
        LinkedHashMap<String, Long> barcodeCountMap = new LinkedHashMap<>();

        for (String barcode : barcodes) {
            barcodeCountMap.putIfAbsent(barcode, (long) 0);
            barcodeCountMap.put(barcode, barcodeCountMap.get(barcode) + 1);
        }

        return barcodeCountMap;
    }

    /*
    P:2min
    D:2min
    C:all good
    A:no need
    */
    private int calculateSubTotal(String barcode, Long quantity) {
        List<ItemInfo> listOfItemInfo = ItemDataLoader.loadAllItemInfos();
        ItemInfo itemInfo = listOfItemInfo.stream().filter(e -> e.getBarcode().equals(barcode)).findAny().orElse(null);
        return (int) (itemInfo.getPrice() * quantity);
    }

    /*
    P:2min
    D:2min
    C:all good
    A:no need
    */
    private String generateSubStringForItem(String barcode, long quantity) {
        List<ItemInfo> listOfItemInfo = ItemDataLoader.loadAllItemInfos();
        ItemInfo itemInfo = listOfItemInfo.stream().filter(e -> e.getBarcode().equals(barcode)).findAny().orElse(null);
        return "Name: " + itemInfo.getName() + ", Quantity: " + quantity + ", Unit price: " + itemInfo.getPrice() + " (yuan), Subtotal: " + quantity * itemInfo.getPrice() + " (yuan)\n";
    }
}
