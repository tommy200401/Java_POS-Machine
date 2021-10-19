package pos.machine;

import java.util.*;

public class Main {
    public static List<String> barcodes = ItemDataLoader.loadBarcodes();
    public static List<ItemInfo> itemInfos = ItemDataLoader.loadAllItemInfos();
    public static List<ItemInfo> barcodesItem = new ArrayList<>();
    public static void main(String[] arg) {

            printItem();
        System.out.println(countItemQuantity(barcodesItem.get(0).getBarcode()));//4
        System.out.println(countSubTotal(barcodes.get(0)));
        findBarcodeOnce();
        System.out.println(countTotal());

    }
    public static void printItem(){

        for (int i = 0; i < barcodes.size(); i++) {
            for (int j = 0; j < itemInfos.size(); j++) {
                if (barcodes.get(i).equals(itemInfos.get(j).getBarcode())) {
                    barcodesItem.add(itemInfos.get(j));
                }
            }
//            System.out.println(barcodesItem.get(i).getBarcode());
//
//            System.out.println(barcodesItem.get(i).getName());
//            System.out.println(barcodesItem.get(i).getPrice());
        }

    }
    public static int countItemQuantity(String barcode){
        int count = 0;
        for(int i=0;i<barcodes.size();i++){
            if(barcode.equals(barcodes.get(i))){
                count++;
            }
        }
        return count;
    }

    public static int findBarcodeIndex(String barcode){
        int index = 0;
        for(int i=0;i<barcodesItem.size();i++){
            if(barcode.equals(barcodesItem.get(i).getBarcode())){
                index = i;
            }
        }
        return index;
    }

    public static int countSubTotal(String barcode){
        int count = countItemQuantity(barcode);
        int index = findBarcodeIndex(barcode);
        int subTotal = barcodesItem.get(index).getPrice()*count;
        return subTotal;
    }

    public static int countTotal(){
//        List<String> existBarcodes
        List<String> onlyBarcodes =new ArrayList<>();
        onlyBarcodes = findBarcodeOnce();
        int sumTotal = 0;
        for (int i=0;i<onlyBarcodes.size();i++){
            sumTotal += countSubTotal(onlyBarcodes.get(i)) ;
        }
        return sumTotal;
    }

    public  static List<String> findBarcodeOnce(){
        List<String> sortedBarcodes = ItemDataLoader.loadBarcodes();
        Collections.sort(sortedBarcodes);
        List<String> onlyBarcodes =new ArrayList<>();
        onlyBarcodes.add(sortedBarcodes.get(0));
        for (int i = 0; i < barcodes.size()-1; i++) {
            if(!sortedBarcodes.get(i).equals(sortedBarcodes.get(i+1))){
                onlyBarcodes.add(sortedBarcodes.get(i+1));

            }

        }
        System.out.println(onlyBarcodes);
        return onlyBarcodes;
    }
}
