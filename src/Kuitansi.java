public class Kuitansi {
    public static void cetak(KohiSop kohiSop, ChannelPembayaran channel, MataUang mataUang) {
        System.out.println("\n=======================================================");
        System.out.println("                   KUITANSI PEMBELIAN");
        System.out.println("=======================================================");

        double totalHargaLuarPajak = 0;
        double totalPajakKeseluruhan = 0;

        //cetak makanan
        System.out.println("\n[ MAKANAN ]");
        boolean hasMakanan = false;
        for (int i = 0; i < kohiSop.getJumlahPesanan(); i++) {
            ItemPesanan ip = kohiSop.getPesanan(i);
            if (ip.getMenu() instanceof Makanan) {
                hasMakanan = true;
                tampilkanItemKuitansi(ip);
                totalHargaLuarPajak += ip.getTotalHarga();
                totalPajakKeseluruhan += ip.getPajak();
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
                totalHargaLuarPajak += ip.getTotalHarga();
                totalPajakKeseluruhan += ip.getPajak();
            }
        }
        if (!hasMinuman)
            System.out.println("- Tidak ada minuman yang dipesan.\n");

        double totalHargaDenganPajak = totalHargaLuarPajak + totalPajakKeseluruhan;

        double diskon = channel.hitungDiskon(totalHargaDenganPajak);
        double admin = channel.getBiayaAdmin();
        double totalAkhirIDR = totalHargaDenganPajak - diskon + admin;

        double totalAkhirKonversi = mataUang.konversiDariIDR(totalAkhirIDR);

        System.out.println("-------------------------------------------------------");
        System.out.printf("%-36s : Rp %,12.0f%n", "Total Harga (di luar pajak)", totalHargaLuarPajak);
        System.out.printf("%-36s : Rp %,12.0f%n", "Total Pajak Keseluruhan", totalPajakKeseluruhan);
        System.out.printf("%-36s : Rp %,12.0f%n", "Total Harga (dengan pajak)", totalHargaDenganPajak);
        System.out.println("-------------------------------------------------------");
        System.out.printf("%-36s : Rp %,12.0f%n", "Diskon (" + channel.getNama() + ")", diskon);
        System.out.printf("%-36s : Rp %,12.0f%n", "Biaya Admin", admin);
        System.out.println("-------------------------------------------------------");
        System.out.printf("%-36s : Rp %,12.0f%n", "Total Tagihan Akhir (IDR)", totalAkhirIDR);

        if (!mataUang.getKode().equals("IDR")) {
            System.out.printf("%-36s : %s %,12.2f%n", "Total Tagihan (" + mataUang.getKode() + ")", mataUang.getKode(),
                    totalAkhirKonversi);
        }

        System.out.println("=======================================================");
        System.out.println("       Terima kasih dan silakan datang kembali!        ");
        System.out.println("=======================================================");
    }

    private static void tampilkanItemKuitansi(ItemPesanan ip) {
        Menu m = ip.getMenu();
        System.out.printf("%-4s %s%n", m.getKode(), m.getNamaMenu());
        System.out.printf("     %d porsi @ Rp %,d%n", ip.getKuantitas(), m.getHarga());
        System.out.printf("     %-33s Rp %,12d%n", "Total Harga", ip.getTotalHarga());
        System.out.printf("     %-33s Rp %,12.0f%n", "Pajak", ip.getPajak());
        System.out.println();
    }
}
