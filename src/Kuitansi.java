public class Kuitansi {
    public static void cetak(KohiSop kohiSop) {
        System.out.println("\n=======================================================");
        System.out.println("                   KUITANSI PEMBELIAN");
        System.out.println("=======================================================");

        double totalHarga = 0;

        //cetak makanan
        System.out.println("\n[ MAKANAN ]");
        boolean hasMakanan = false;
        for (int i = 0; i < kohiSop.getJumlahPesanan(); i++) {
            ItemPesanan ip = kohiSop.getPesanan(i);
            if (ip.getMenu() instanceof Makanan) {
                hasMakanan = true;
                tampilkanItemKuitansi(ip);
                totalHarga += ip.getTotalHarga();
            }
        }
        if (!hasMakanan)
            System.out.println("- Tidak ada makanan yang dipesan.\n");

        // cetak minuman
        System.out.println("[ MINUMAN ]");
        boolean hasMinuman = false;
        for (int i = 0; i < kohiSop.getJumlahPesanan(); i++) {
            ItemPesanan ip = kohiSop.getPesanan(i);
            if (ip.getMenu() instanceof Minuman) {
                hasMinuman = true;
                tampilkanItemKuitansi(ip);
                totalHarga += ip.getTotalHarga();
            }
        }
        if (!hasMinuman)
            System.out.println("- Tidak ada minuman yang dipesan.\n");




        System.out.println("-------------------------------------------------------");
        System.out.printf("%-36s : Rp %,12.0f%n", "Total Tagihan", totalHarga);
        System.out.println("-------------------------------------------------------");

        System.out.println("=======================================================");
        System.out.println("       Terima kasih dan silakan datang kembali!        ");
        System.out.println("=======================================================");
    }

    private static void tampilkanItemKuitansi(ItemPesanan ip) {
        Menu m = ip.getMenu();
        System.out.printf("%-4s %s%n", m.getKode(), m.getNamaMenu());
        System.out.printf("     %d porsi @ Rp %,d%n", ip.getKuantitas(), m.getHarga());
        System.out.printf("     %-33s Rp %,12d%n", "Total Harga", ip.getTotalHarga());

        System.out.println();
    }
}
