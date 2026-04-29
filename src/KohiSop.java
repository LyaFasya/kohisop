public class KohiSop {
    private static final Menu[] DAFTAR_MENU = {
        // minuman
        new Minuman("A1", "Caffe Latte",46000),
        new Minuman("A2", "Cappuccino",46000),
        new Minuman("E1", "Caffe Americano",37000),
        new Minuman("E2", "Caffe Mocha",55000),
        new Minuman("E3", "Caramel Macchiato",59000),
        new Minuman("E4", "Asian Dolce Latte",55000),
        new Minuman("E5", "Double Shots Iced Shaken Espresso",50000),
        new Minuman("B1", "Freshly Brewed Coffee",23000),
        new Minuman("B2", "Vanilla Sweet Cream Cold Brew",50000),
        new Minuman("B3", "Cold Brew",44000),
        // makanan
        new Makanan("M1", "Petemania Pizza",112000),
        new Makanan("M2", "Mie Rebus Super Mario",35000),
        new Makanan("M3", "Ayam Bakar Goreng Rebus Spesial",72000),
        new Makanan("M4", "Soto Kambing Iga Guling",124000),
        new Makanan("S1", "Singkong Bakar A La Carte",37000),
        new Makanan("S2", "Ubi Cilembu Bakar Arang",58000),
        new Makanan("S3", "Tempe Mendoan",18000),
        new Makanan("S4", "Tahu Bakso Extra Telur",28000)
    };

    public static final int MAX_JENIS_PER_KATEGORI = 5;
    private final ItemPesanan[] pesanan = new ItemPesanan[MAX_JENIS_PER_KATEGORI * 2];
    private int jumlahPesanan = 0;
    public static boolean isKodeValid(String kode) {
        for (int i = 0; i < DAFTAR_MENU.length; i++) {
            if (DAFTAR_MENU[i].getKode().equalsIgnoreCase(kode)) {
                return true;
            }
        }
        return false;
    }

    public static Menu cariMenu(String kode) {
        for (int i = 0; i < DAFTAR_MENU.length; i++) {
            if (DAFTAR_MENU[i].getKode().equalsIgnoreCase(kode)) {
                return DAFTAR_MENU[i];
            }
        }
        return null;
    }

    public boolean sudahDipesan(String kode) {
        for (int i = 0; i < jumlahPesanan; i++) {
            if (pesanan[i].getMenu().getKode().equalsIgnoreCase(kode)) {
                return true;
            }
        }
        return false;
    }

    public int jumlahJenisDipesan(String kategori) {
        int count = 0;
        for (int i = 0; i < jumlahPesanan; i++) {
            if (pesanan[i].getMenu().getKategori().equalsIgnoreCase(kategori)) {
                count++;
            }
        }
        return count;
    }

    public void tambahPesanan(Menu menu, int kuantitas) {
        if (jumlahPesanan < pesanan.length) {
            pesanan[jumlahPesanan] = new ItemPesanan(menu, kuantitas);
            jumlahPesanan++;
        }
    }

    public ItemPesanan getPesanan(int indeks) {
        return pesanan[indeks];
    }
    public int getJumlahPesanan() {
        return jumlahPesanan;
    }

    public boolean pesananKosong() {
        return jumlahPesanan == 0;
    }
    public void hapusPesananKosong() {
        int baru = 0;
        for (int i = 0; i < jumlahPesanan; i++) {
            if (pesanan[i].getKuantitas() > 0) {
                pesanan[baru] = pesanan[i];
                baru++;
            }
        }
        for (int i = baru; i < jumlahPesanan; i++) {
            pesanan[i] = null;
        }
        jumlahPesanan = baru;
    }
    // tampilan menu
    public static void tampilkanDaftarMenu() {
        String garis = "=".repeat(58);
        System.out.println(garis);
        System.out.println("                       MENU KOHISOP");
        System.out.println(garis);

        // minuman
        System.out.printf("%-4s | %-36s | %s%n", "Kode", "Nama Menu Minuman", "Harga (Rp)");
        System.out.println("-".repeat(58));
        for (int i = 0; i < DAFTAR_MENU.length; i++) {
            if (DAFTAR_MENU[i] instanceof Minuman) {
                System.out.printf("%-4s | %-36s | Rp %,d%n",
                    DAFTAR_MENU[i].getKode(),
                    DAFTAR_MENU[i].getNamaMenu(),
                    DAFTAR_MENU[i].getHarga());
            }
        }

        System.out.println();

        // makanan
        System.out.printf("%-4s | %-36s | %s%n", "Kode", "Nama Menu Makanan", "Harga (Rp)");
        System.out.println("-".repeat(58));
        for (int i = 0; i < DAFTAR_MENU.length; i++) {
            if (DAFTAR_MENU[i] instanceof Makanan) {
                System.out.printf("%-4s | %-36s | Rp %,d%n",
                    DAFTAR_MENU[i].getKode(),
                    DAFTAR_MENU[i].getNamaMenu(),
                    DAFTAR_MENU[i].getHarga());
            }
        }
        System.out.println(garis);
    }

    // tabel pesanan untuk isi kuantitas
    public void tampilkanTabelPesanan() {
        if (jumlahPesanan == 0) return;
        String garis = "-".repeat(52);

        // minuman
        boolean adaMinuman = false;
        for (int i = 0; i < jumlahPesanan; i++) {
            if (pesanan[i].getMenu() instanceof Minuman) { adaMinuman = true; break; }
        }
        if (adaMinuman) {
            System.out.println(garis);
            System.out.printf("%-4s | %-33s | %s%n", "Kode", "Minuman", "Kuantitas");
            System.out.println(garis);
            for (int i = 0; i < jumlahPesanan; i++) {
                if (pesanan[i].getMenu() instanceof Minuman) {
                    System.out.printf("%-4s | %-33s | %d%n",
                        pesanan[i].getMenu().getKode(),
                        pesanan[i].getMenu().getNamaMenu(),
                        pesanan[i].getKuantitas());
                }
            }
        }

        // makanan
        boolean adaMakanan = false;
        for (int i = 0; i < jumlahPesanan; i++) {
            if (pesanan[i].getMenu() instanceof Makanan) { adaMakanan = true; break; }
        }
        if (adaMakanan) {
            System.out.println(garis);
            System.out.printf("%-4s | %-33s | %s%n", "Kode", "Makanan", "Kuantitas");
            System.out.println(garis);
            for (int i = 0; i < jumlahPesanan; i++) {
                if (pesanan[i].getMenu() instanceof Makanan) {
                    System.out.printf("%-4s | %-33s | %d%n",
                        pesanan[i].getMenu().getKode(),
                        pesanan[i].getMenu().getNamaMenu(),
                        pesanan[i].getKuantitas());
                }
            }
        }
        System.out.println(garis);
    }
}
