package dk.ss.backendtshirt.common.data;

import dk.ss.backendtshirt.tshirt.model.Product;
import dk.ss.backendtshirt.tshirt.model.GiftProduct;
import dk.ss.backendtshirt.tshirt.model.GiftConfig;
import dk.ss.backendtshirt.tshirt.repository.ProductRepository;
import dk.ss.backendtshirt.tshirt.repository.GiftProductRepository;
import dk.ss.backendtshirt.tshirt.repository.GiftConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Profile("!test")  // Kør IKKE denne class når test profil er aktiv
public class initData implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final GiftProductRepository giftProductRepository;
    private final GiftConfigRepository giftConfigRepository;

    @Autowired
    public initData(ProductRepository productRepository,
                   GiftProductRepository giftProductRepository,
                   GiftConfigRepository giftConfigRepository) {
        this.productRepository = productRepository;
        this.giftProductRepository = giftProductRepository;
        this.giftConfigRepository = giftConfigRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Only initialize if database is empty
        if (productRepository.count() == 0 && giftProductRepository.count() == 0) {
            initializeProducts();
            initializeGiftProducts();
            initializeGiftConfig();
        }
    }

    private void initializeProducts() {
        // ========== BASIC T-SHIRTS (Billige) ==========
        Product product1 = new Product(
                "Klassisk Hvid T-Shirt",
                "100% bomuld. Perfekt til hverdagen. Maskinevaskes ved 40°C. Regular fit.",
                new BigDecimal("79.00"),
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQrZFgx9Ax5epJQliq69Z4nQ_97pG6ZVCt8_A&s",
                "M",
                "WHITE",
                150,
                true
        );

        Product product2 = new Product(
                "Klassisk Sort T-Shirt",
                "100% bomuld. Tidløs sort t-shirt i blød kvalitet. Holder formen vask efter vask.",
                new BigDecimal("79.00"),
                "https://hiddentrend.dk/cdn/shop/files/Basic_T-shirt_Sort_1.jpg?v=1738268164",
                "L",
                "BLACK",
                200,
                true
        );

        Product product3 = new Product(
                "Klassisk Grå T-Shirt",
                "Meleret grå. 100% bomuld. Behagelig pasform der ikke krymper. Perfekt basistøj.",
                new BigDecimal("79.00"),
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQo8QxyzMPuxUsCr8I9hwhi2qmL_6ju9M0yZg&s",
                "S",
                "GRAY",
                120,
                true
        );

        Product product4 = new Product(
                "Navy Blå Basic T-Shirt",
                "Dyb navy blå farve. 100% økologisk bomuld. Klassisk snit der passer til alt.",
                new BigDecimal("89.00"),
                "https://img01.ztat.net/article/spp-media-p1/0e5627cbeb7a42f5aa8d35cb995eb86b/307521ed78f74871893cb4834dac5794.jpg?imwidth=1800&filter=packshot",
                "M",
                "BLUE",
                100,
                true
        );

        // ========== FARVERIGE T-SHIRTS (Mellem) ==========
        Product product5 = new Product(
                "Frisk Mint Grøn T-Shirt",
                "Sommerfarve i mint grøn. Lækker blød bomuld. Moderne pasform med lidt stretch.",
                new BigDecimal("99.00"),
                "https://img01.ztat.net/article/spp-media-p1/984f8c38a99a46de8f45cb61a17c7c0e/160c8ab571d344b38114180fde19ef4f.png?imwidth=762&filter=packshot",
                "L",
                "GREEN",
                80,
                true
        );

        Product product6 = new Product(
                "Solskin Gul T-Shirt",
                "Lys gul - perfekt til forår og sommer. 95% bomuld, 5% elastan for bedre pasform.",
                new BigDecimal("99.00"),
                "https://static.molo.com/molo-style/-dreng-t-shirts-bluser-riley-smile-pixel-/1w24a206_3686_b.jpg?width=1000&height=1200&trim.threshold=90&bgcolor=white",
                "M",
                "YELLOW",
                60,
                true
        );

        Product product7 = new Product(
                "Vintage Orange T-Shirt",
                "Varm orange tone i vintage look. Pre-washed for blød følelse fra start.",
                new BigDecimal("109.00"),
                "https://d2akct5dekqm4p.cloudfront.net/57/media/1600x1200/96488-25vm000l-t-shirt-harley-super-glide-fat-bob-149309.jpg",
                "XL",
                "RED",
                45,
                true
        );

        Product product8 = new Product(
                "Dyb Bordeaux Rød T-Shirt",
                "Eksklusiv bordeaux farve. Premium bomuld fra Portugal. Slim fit model.",
                new BigDecimal("129.00"),
                "https://img01.ztat.net/article/spp-media-p1/c3d93653e4d94137a780f050691f7c5f/d80e0599165241c0b60be5633059ec52.jpg?imwidth=762",
                "L",
                "RED",
                70,
                true
        );

        // ========== PREMIUM T-SHIRTS (Dyre) ==========
        Product product9 = new Product(
                "Premium Oversized White Tee",
                "Ekstra blød supima bomuld. Oversized streetwear fit. Forstærket nakke og skulder.",
                new BigDecimal("149.00"),
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSDne90s6wUr2ir5co8ED07ev9ymJ5KGJQx0Q&s",
                "XL",
                "WHITE",
                50,
                true
        );

        Product product10 = new Product(
                "Stribet Bretonsk T-Shirt",
                "Klassisk fransk stil med brun/hvide striber. 100% økologisk bomuld. Regular fit.",
                new BigDecimal("139.00"),
                "https://cdn.laredoute.com/cdn-cgi/image/width=400,height=400,fit=pad,dpr=1/products/b/e/6/be61f5b462f6daa2a0a9f452940939f7.jpg",
                "M",
                "BLUE",
                40,
                true
        );

        Product product11 = new Product(
                "Pocket Tee - Sort Med Lomme",
                "Sort t-shirt med brystlomme. Tykkere stof (200g). Perfekt til efterår.",
                new BigDecimal("119.00"),
                "https://lsco.scene7.com/is/image/lsco/865840069-alt1-pdp-lse?fmt=jpeg&qlt=70&resMode=sharp2&fit=crop,1&op_usm=0.6,0.6,8&wid=2000&hei=2500",
                "L",
                "BLACK",
                65,
                true
        );

        Product product12 = new Product(
                "Vintage Washed Olive T-Shirt",
                "Olive grøn med vintage wash. Unik farve på hver t-shirt. Boxy fit.",
                new BigDecimal("159.00"),
                "https://freshblanks.com/cdn/shop/files/olive-green-vintage-t-shirt-front.jpg?v=1724709311&width=1400",
                "XL",
                "GREEN",
                30,
                true
        );

        // ========== SPECIELLE MODELLER ==========
        Product product13 = new Product(
                "Henley T-Shirt - Navy",
                "Henley model med knaplukning. 100% bomuld. Perfekt til smart casual.",
                new BigDecimal("169.00"),
                "https://triprindia.com/cdn/shop/files/TI.NVHN-D162_1.jpg?v=1737536424",
                "M",
                "BLUE",
                35,
                true
        );

        Product product14 = new Product(
                "Raglan T-Shirt - Grå/Sort",
                "Baseball-inspireret raglan ærme. Sporty look i grå/sort kombination.",
                new BigDecimal("129.00"),
                "https://2trendy.dk/cdn/shop/files/d47d5f95ef8b00af740c78b2ada1_54dcb6f0-0a08-4f5b-bba5-bcb04cae43a0.jpg?v=1759413904&width=800",
                "L",
                "GRAY",
                40,
                true
        );

        Product product15 = new Product(
                "V-Hals Basic - Hvid",
                "Hvid t-shirt med v-hals. Elegant snit. Perfekt under blazer eller alene.",
                new BigDecimal("99.00"),
                "https://brdr-simonsen.dk/cdn/shop/products/BASIC_TSHIRT_VNECK_HVID_FRONT.jpg?v=1718629708&width=1080",
                "M",
                "WHITE",
                85,
                true
        );

        // ========== UDSOLGTE/INACTIVE (Test data) ==========
        Product product16 = new Product(
                "Limited Edition Pink T-Shirt",
                "Udsolgt bestseller i lyserød. Var vores mest solgte forårsstil.",
                new BigDecimal("89.00"),
                "https://cdn.clothbase.com/uploads/a5d9f4d9-8af2-4333-94d1-7c273df33332/211669M213003_1.jpg",
                "S",
                "RED",
                0,
                false
        );

        Product product17 = new Product(
                "Vinter Edition - Tykt Stof",
                "Sæsonvare - Udgået. Vinter t-shirt i ekstra tykt stof.",
                new BigDecimal("149.00"),
                "https://hf-hcms-staging1.azureedge.net/029/2512-0400.jpg_Original_638989318665930000.jpg?w=3840&h=3840&bgcolor=ffffff&format=jpg",
                "L",
                "BLACK",
                0,
                false
        );

        // Save all products
        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
        productRepository.save(product4);
        productRepository.save(product5);
        productRepository.save(product6);
        productRepository.save(product7);
        productRepository.save(product8);
        productRepository.save(product9);
        productRepository.save(product10);
        productRepository.save(product11);
        productRepository.save(product12);
        productRepository.save(product13);
        productRepository.save(product14);
        productRepository.save(product15);
        productRepository.save(product16);
        productRepository.save(product17);

        System.out.println("✅ Regular products initialized: 17 products created");
        System.out.println("   - Active regular products: 15");
        System.out.println("   - Inactive regular products: 2");
        System.out.println("   - Price range: 79-169 kr");
    }

    private void initializeGiftProducts() {
        // ========== GRATIS GAVER (Ved køb over 500 kr) ==========

        GiftProduct gift1 = new GiftProduct(
                "Gratis Nøglering - Minimalistisk Design",
                "Eksklusiv metallic nøglering med logo. Nordisk minimalistisk design i børstet stål.",
                "https://www.bestman.dk/images/10021469.webp",
                500,
                true
        );

        GiftProduct gift2 = new GiftProduct(
                "Gratis Bomuldstaske - Naturfarvet",
                "Økologisk bomuldstaske med sort logo-tryk. Perfekt til indkøb. 38x42 cm.",
                "https://www.onlineprinters.dk/$WS/diedruckerei/shopdata/media/pim/images/500x500/jpg/2c9ceb817fe031bf018022ce5ce923ec.jpg",
                300,
                true
        );

        GiftProduct gift3 = new GiftProduct(
                "Gratis Strikkesokker - Vinter Edition",
                "Varme uldstrikkesokker i nordisk design. Uni-størrelse (37-44). Limited stock!",
                "https://rito.dk/img/cms/opskriftsbilleder/kit-rito-0046%20bambus%20sokker/20191128_170333.jpg",
                150,
                true
        );

        GiftProduct gift4 = new GiftProduct(
                "Gratis Klistermærke Pack - 5 stk",
                "Pakke med 5 forskellige vinyl klistermærker. Waterproof. Perfekt til laptop!",
                "https://stickerland.dk/media/webp_image/catalog/product/cache/d3be0de455e072a242b4c7ad76d16818/b/l/blandede_klisterm_rker_plast_soft.webp",
                800,
                true
        );

        GiftProduct gift5 = new GiftProduct(
                "Gratis Snapback Cap - Sort",
                "Sort snapback kasket med broderet logo. Justerbar størrelse. Street style.",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSDsemQM_CtMwRGw2-qBZ5uwaQ2lSvbekrf2A&s",
                120,
                true
        );

        GiftProduct gift6 = new GiftProduct(
                "Gratis Vand Flaske - Rustfrit Stål",
                "500ml vandflaske i rustfrit stål. Holder koldt i 24 timer. Lækagesikker.",
                "https://grontkontor.dk/wp-content/uploads/2020/08/vandflaske-staal-valgfri-farve-01.jpg",
                80,
                true
        );

        // ========== UDSOLGTE GAVER (Test scenarios) ==========

        GiftProduct gift7 = new GiftProduct(
                "Gratis Limited Beanie - UDSOLGT",
                "Udsolgt! Varm beanie i uld-blend. Var meget populær i vinter sæsonen.",
                "https://cdn.billig-arbejdstoj.dk/media/lhcfgmt4/7244029/150075_10.jpg?width=705&height=705&bgcolor=ffffff&format=webp&quality=75",
                0,
                false
        );

        GiftProduct gift8 = new GiftProduct(
                "Gratis Sommerhue - Sæsonvare",
                "Udgået sæsonvare. Bucket hat i beige. Kommer igen til sommer 2026.",
                "https://hatshop.dk/images/zoom/hattar_garda_wynn_bucket_hat_beige_02.jpg",
                0,
                false
        );

        GiftProduct gift9 = new GiftProduct(
                "Gratis Telefon Holder - UDSOLGT",
                "Populær gave - midlertidigt udsolgt. Magnetisk bil telefon holder.",
                "https://productimages.biltema.com/v1/Image/product/xlarge/2000042894/4",
                0,
                false
        );

        // Save all gift products
        giftProductRepository.save(gift1);
        giftProductRepository.save(gift2);
        giftProductRepository.save(gift3);
        giftProductRepository.save(gift4);
        giftProductRepository.save(gift5);
        giftProductRepository.save(gift6);
        giftProductRepository.save(gift7);
        giftProductRepository.save(gift8);
        giftProductRepository.save(gift9);

        System.out.println("✅ Gift products initialized: 9 gift products created");
        System.out.println("   - Active gift products: 6");
        System.out.println("   - Inactive gift products: 3");
        System.out.println("   - Total gift stock: 1950 units");
    }

    /**
     * Initialize gift configuration with threshold for free gift eligibility
     */
    private void initializeGiftConfig() {
        if (giftConfigRepository.count() == 0) {
            GiftConfig config = new GiftConfig();
            config.setThresholdAmount(new BigDecimal("500.00")); // Gave ved køb over 500 kr
            config.setActive(1);                                  // Aktiv
            config.setGiftProductId(1L);                          // Default gave: Nøglering (ID 1)

            giftConfigRepository.save(config);

            System.out.println("✅ Gift configuration initialized");
            System.out.println("   - Threshold: 500 kr");
            System.out.println("   - Status: Active");
            System.out.println("   - Default gift: Nøglering (ID 1)");
        }
    }
}
