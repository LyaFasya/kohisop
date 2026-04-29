import java.util.Scanner;

public class App {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("==========================================================");
        System.out.println("                Selamat Datang di KohiSop!                ");
        System.out.println("==========================================================");

        KohiSop kohiSop = new KohiSop();
        KohiSop.tampilkanDaftarMenu();
        System.out.println("\nPilih Minuman");
        System.out.println("Maks " + KohiSop.MAX_JENIS_PER_KATEGORI + " jenis minuman.");
        System.out.println("Ketik 'DONE' jika sudah selesai, 'CC' untuk membatalkan pesanan.");

        while (kohiSop.jumlahJenisDipesan("Minuman") < KohiSop.MAX_JENIS_PER_KATEGORI) {
            System.out.print("Kode Minuman: ");
            String input = sc.nextLine().trim().toUpperCase();
            if (input.equals("CC")) {
                System.out.println("Pesanan dibatalkan.");
                return;
            }
            if (input.equals("DONE"))
                break;
            if (!KohiSop.isKodeValid(input)) {
                System.out.println("Kode tidak valid, coba lagi ya.");
                continue;
            }

            Menu menu = KohiSop.cariMenu(input);
            if (!(menu instanceof Minuman)) {
                System.out.println("Tolong masukkan kode untuk minuman.");
                continue;
            }
            if (kohiSop.sudahDipesan(input)) {
                System.out.println("Minuman ini sudah kamu pilih.");
                continue;
            }
            kohiSop.tambahPesanan(menu, 1);
            System.out.println("=> " + menu.getNamaMenu() + " ditambahkan!");
        }

        if (kohiSop.jumlahJenisDipesan("Minuman") >= KohiSop.MAX_JENIS_PER_KATEGORI) {
            System.out.println("Udah mentok 5 jenis minuman nih.");
        }
        System.out.println("\nPilih Makanan");
        System.out.println("Maks " + KohiSop.MAX_JENIS_PER_KATEGORI + " jenis makanan.");
        System.out.println("Ketik 'DONE' jika sudah selesai, 'CC' untuk membatalkan pesanan.");
        while (kohiSop.jumlahJenisDipesan("Makanan") < KohiSop.MAX_JENIS_PER_KATEGORI) {
            System.out.print("Kode Makanan: ");
            String input = sc.nextLine().trim().toUpperCase();
            if (input.equals("CC")) {
                System.out.println("Pesanan dibatalkan.");
                return;
            }
            if (input.equals("DONE"))
                break;
            if (!KohiSop.isKodeValid(input)) {
                System.out.println("Kode tidak valid, coba lagi ya.");
                continue;
            }

            Menu menu = KohiSop.cariMenu(input);
            if (!(menu instanceof Makanan)) {
                System.out.println("Tolong masukkan kode untuk makanan.");
                continue;
            }
            if (kohiSop.sudahDipesan(input)) {
                System.out.println("Makanan ini sudah kamu pilih.");
                continue;
            }
            kohiSop.tambahPesanan(menu, 1);
            System.out.println("=> " + menu.getNamaMenu() + " ditambahkan!");
        }

        if (kohiSop.jumlahJenisDipesan("Makanan") >= KohiSop.MAX_JENIS_PER_KATEGORI) {
            System.out.println("Udah mentok 5 jenis makanan nih.");
        }
        if (kohiSop.pesananKosong()) {
            System.out.println("Kamu belum pesan apa-apa. Keluar program...");
            return;
        }
        for (int i = 0; i < kohiSop.getJumlahPesanan(); i++) {
            kohiSop.getPesanan(i).setKuantitas(0);
        }

        System.out.println("\n[Input Kuantitas]");
        System.out.println("Enter = 1 porsi (default) | 0 atau S = skip | CC = batalkan");
        for (int i = 0; i < kohiSop.getJumlahPesanan(); i++) {
            ItemPesanan ip = kohiSop.getPesanan(i);
            Menu menu = ip.getMenu();
            int maxQty = menu.getMaxKuantitas();
            System.out.println();
            kohiSop.tampilkanTabelPesanan();

            System.out.printf("Kuantitas [%s] %s (maks %d, default 1): ",
                    menu.getKode(), menu.getNamaMenu(), maxQty);
            String inputQty = sc.nextLine().trim();

            // batal
            if (inputQty.equalsIgnoreCase("CC")) {
                System.out.println("Batal pesan deh.");
                return;
            }

            // skip
            if (inputQty.equalsIgnoreCase("S") || inputQty.equals("0")) {
                ip.setKuantitas(0);
                System.out.println("=> " + menu.getNamaMenu() + " diskip.");
                continue;
            }

            // enter aja langsung default 1
            if (inputQty.isEmpty()) {
                ip.setKuantitas(1);
                System.out.println("=> Kuantitas set 1");
                continue;
            }

            // ambil input angkanya
            boolean valid = false;
            while (!valid) {
                try {
                    int qty = Integer.parseInt(inputQty);

                    if (qty < 0) {
                        System.out.print("Jangan negatif dong, masukin lagi: ");
                    } else if (qty == 0) {
                        ip.setKuantitas(0);
                        System.out.println("=> " + menu.getNamaMenu() + " diskip.");
                        valid = true;
                    } else if (qty > maxQty) {
                        System.out.printf("Maksimal cuma %d porsi. Masukin lagi: ", maxQty);
                    } else {
                        ip.setKuantitas(qty);
                        System.out.printf("=> %s x%d%n", menu.getNamaMenu(), qty);
                        valid = true;
                    }
                } catch (NumberFormatException e) {
                    System.out.print("Harus angka ya! Masukin lagi: ");
                }

                if (!valid) {
                    inputQty = sc.nextLine().trim();
                    if (inputQty.equalsIgnoreCase("CC")) {
                        System.out.println("Batal pesan deh.");
                        return;
                    }
                    if (inputQty.equalsIgnoreCase("S") || inputQty.equals("0")) {
                        ip.setKuantitas(0);
                        System.out.println("=> " + menu.getNamaMenu() + " diskip.");
                        valid = true;
                    }
                    if (inputQty.isEmpty()) {
                        ip.setKuantitas(1);
                        System.out.println("=> Kuantitas set 1");
                        valid = true;
                    }
                }
            }
        }

        // buang item yang tidak jadi dipesan
        kohiSop.hapusPesananKosong();

        if (kohiSop.pesananKosong()) {
            System.out.println("Semua item di skip. Keluar program...");
            return;
        }

        // detail pesanan dan hitung total sementara
        System.out.println("\n[Detail Pesanan]");
        double totalPesanan= 0;
        for (int i = 0; i < kohiSop.getJumlahPesanan(); i++) {
            ItemPesanan ip = kohiSop.getPesanan(i);
            Menu m = ip.getMenu();
            int qty = ip.getKuantitas();
            int totalHargaItem = ip.getTotalHarga();
            
            System.out.printf("- %s (x%d) : Rp %,d%n", 
                m.getNamaMenu(), qty, totalHargaItem);
            totalPesanan += totalHargaItem;
        }
        System.out.println("--------------------------------------------------");
        System.out.printf("Total Pesanan : Rp %,.0f%n", totalPesanan);

        // cetak kuitansi
        Kuitansi.cetak(kohiSop);
    }
}