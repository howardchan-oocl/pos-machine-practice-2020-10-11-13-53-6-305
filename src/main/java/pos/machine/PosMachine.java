package pos.machine;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PosMachine {
    private long total=0;
    public static void main(String[] args) {
        List<String> listOfBarcode = ItemDataLoader.loadBarcodes();

        PosMachine posMachine = new PosMachine();
        System.out.println(posMachine.printReceipt(listOfBarcode));
    }

    public String printReceipt(List<String> barcodes) {
        //Map<String, Long> barcodeMap = barcodes.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        Map<String, Long> barcodeMap2 = new LinkedHashMap<>();
        for(String barcode : barcodes)
        {
            if(barcodeMap2.containsKey(barcode))
            {
                barcodeMap2.put(barcode,barcodeMap2.get(barcode)+1);
            }else
            {
                barcodeMap2.put(barcode,(long)1);
            }
        }
        String receipt = "***<store earning no money>Receipt***\n";
        for (String i : barcodeMap2.keySet()) {

            String subReceipt = generateSubStringForItem(i, barcodeMap2.get(i));
            receipt += subReceipt;
        }
        receipt += "----------------------\nTotal: " + total + " (yuan)\n**********************";
        return receipt;
    }

    private String generateSubStringForItem(String barcode, long quantity) {
        List<ItemInfo> listOfItemInfo = ItemDataLoader.loadAllItemInfos();
        ItemInfo itemInfo = listOfItemInfo.stream().filter(e->e.getBarcode().equals(barcode)).findAny().orElse(null);
        total += itemInfo.getPrice() * quantity;
        return "Name: " + itemInfo.getName() + ", Quantity: " + quantity + ", Unit price: " + itemInfo.getPrice() + " (yuan), Subtotal: " + quantity * itemInfo.getPrice() + " (yuan)\n";
    }
}
