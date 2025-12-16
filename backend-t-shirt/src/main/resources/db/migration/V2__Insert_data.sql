-- ============================================
-- T-SHIRT WEBSHOP - DATABASE SEED DATA
-- ============================================
-- Generated from initData.java
-- Date: 2025-12-13
-- Med KORREKTE billede URLs fra din opdaterede initData
-- ============================================

-- ============================================
-- 1. PRODUCTS TABLE - 17 T-SHIRTS
-- ============================================

-- BASIC T-SHIRTS (79-89 kr)
INSERT INTO products (id, name, description, price, image_url, size, color, stock_quantity, active, created_at, updated_at) VALUES
(1, 'Klassisk Hvid T-Shirt', '100% bomuld. Perfekt til hverdagen. Maskinevaskes ved 40°C. Regular fit.', 79.00, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQrZFgx9Ax5epJQliq69Z4nQ_97pG6ZVCt8_A&s', 'M', 'WHITE', 150, true, NOW(), NOW()),

(2, 'Klassisk Sort T-Shirt', '100% bomuld. Tidløs sort t-shirt i blød kvalitet. Holder formen vask efter vask.', 79.00, 'https://hiddentrend.dk/cdn/shop/files/Basic_T-shirt_Sort_1.jpg?v=1738268164', 'L', 'BLACK', 200, true, NOW(), NOW()),

(3, 'Klassisk Grå T-Shirt', 'Meleret grå. 100% bomuld. Behagelig pasform der ikke krymper. Perfekt basistøj.', 79.00, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQo8QxyzMPuxUsCr8I9hwhi2qmL_6ju9M0yZg&s', 'S', 'GRAY', 120, true, NOW(), NOW()),

(4, 'Navy Blå Basic T-Shirt', 'Dyb navy blå farve. 100% økologisk bomuld. Klassisk snit der passer til alt.', 89.00, 'https://img01.ztat.net/article/spp-media-p1/0e5627cbeb7a42f5aa8d35cb995eb86b/307521ed78f74871893cb4834dac5794.jpg?imwidth=1800&filter=packshot', 'M', 'BLUE', 100, true, NOW(), NOW()),

-- FARVERIGE T-SHIRTS (99-129 kr)
(5, 'Frisk Mint Grøn T-Shirt', 'Sommerfarve i mint grøn. Lækker blød bomuld. Moderne pasform med lidt stretch.', 99.00, 'https://img01.ztat.net/article/spp-media-p1/984f8c38a99a46de8f45cb61a17c7c0e/160c8ab571d344b38114180fde19ef4f.png?imwidth=762&filter=packshot', 'L', 'GREEN', 80, true, NOW(), NOW()),

(6, 'Solskin Gul T-Shirt', 'Lys gul - perfekt til forår og sommer. 95% bomuld, 5% elastan for bedre pasform.', 99.00, 'https://static.molo.com/molo-style/-dreng-t-shirts-bluser-riley-smile-pixel-/1w24a206_3686_b.jpg?width=1000&height=1200&trim.threshold=90&bgcolor=white', 'M', 'YELLOW', 60, true, NOW(), NOW()),

(7, 'Vintage Orange T-Shirt', 'Varm orange tone i vintage look. Pre-washed for blød følelse fra start.', 109.00, 'https://d2akct5dekqm4p.cloudfront.net/57/media/1600x1200/96488-25vm000l-t-shirt-harley-super-glide-fat-bob-149309.jpg', 'XL', 'RED', 45, true, NOW(), NOW()),

(8, 'Dyb Bordeaux Rød T-Shirt', 'Eksklusiv bordeaux farve. Premium bomuld fra Portugal. Slim fit model.', 129.00, 'https://img01.ztat.net/article/spp-media-p1/c3d93653e4d94137a780f050691f7c5f/d80e0599165241c0b60be5633059ec52.jpg?imwidth=762', 'L', 'RED', 70, true, NOW(), NOW()),

-- PREMIUM T-SHIRTS (139-169 kr)
(9, 'Premium Oversized White Tee', 'Ekstra blød supima bomuld. Oversized streetwear fit. Forstærket nakke og skulder.', 149.00, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSDne90s6wUr2ir5co8ED07ev9ymJ5KGJQx0Q&s', 'XL', 'WHITE', 50, true, NOW(), NOW()),

(10, 'Stribet Bretonsk T-Shirt', 'Klassisk fransk stil med brun/hvide striber. 100% økologisk bomuld. Regular fit.', 139.00, 'https://cdn.laredoute.com/cdn-cgi/image/width=400,height=400,fit=pad,dpr=1/products/b/e/6/be61f5b462f6daa2a0a9f452940939f7.jpg', 'M', 'BLUE', 40, true, NOW(), NOW()),

(11, 'Pocket Tee - Sort Med Lomme', 'Sort t-shirt med brystlomme. Tykkere stof (200g). Perfekt til efterår.', 119.00, 'https://lsco.scene7.com/is/image/lsco/865840069-alt1-pdp-lse?fmt=jpeg&qlt=70&resMode=sharp2&fit=crop,1&op_usm=0.6,0.6,8&wid=2000&hei=2500', 'L', 'BLACK', 65, true, NOW(), NOW()),

(12, 'Vintage Washed Olive T-Shirt', 'Olive grøn med vintage wash. Unik farve på hver t-shirt. Boxy fit.', 159.00, 'https://freshblanks.com/cdn/shop/files/olive-green-vintage-t-shirt-front.jpg?v=1724709311&width=1400', 'XL', 'GREEN', 30, true, NOW(), NOW()),

-- SPECIELLE MODELLER
(13, 'Henley T-Shirt - Navy', 'Henley model med knaplukning. 100% bomuld. Perfekt til smart casual.', 169.00, 'https://triprindia.com/cdn/shop/files/TI.NVHN-D162_1.jpg?v=1737536424', 'M', 'BLUE', 35, true, NOW(), NOW()),

(14, 'Raglan T-Shirt - Grå/Sort', 'Baseball-inspireret raglan ærme. Sporty look i grå/sort kombination.', 129.00, 'https://2trendy.dk/cdn/shop/files/d47d5f95ef8b00af740c78b2ada1_54dcb6f0-0a08-4f5b-bba5-bcb04cae43a0.jpg?v=1759413904&width=800', 'L', 'GRAY', 40, true, NOW(), NOW()),

(15, 'V-Hals Basic - Hvid', 'Hvid t-shirt med v-hals. Elegant snit. Perfekt under blazer eller alene.', 99.00, 'https://brdr-simonsen.dk/cdn/shop/products/BASIC_TSHIRT_VNECK_HVID_FRONT.jpg?v=1718629708&width=1080', 'M', 'WHITE', 85, true, NOW(), NOW()),

-- UDSOLGTE/INACTIVE PRODUKTER (Test data)
(16, 'Limited Edition Pink T-Shirt', 'Udsolgt bestseller i lyserød. Var vores mest solgte forårsstil.', 89.00, 'https://cdn.clothbase.com/uploads/a5d9f4d9-8af2-4333-94d1-7c273df33332/211669M213003_1.jpg', 'S', 'RED', 0, false, NOW(), NOW()),

(17, 'Vinter Edition - Tykt Stof', 'Sæsonvare - Udgået. Vinter t-shirt i ekstra tykt stof.', 149.00, 'https://hf-hcms-staging1.azureedge.net/029/2512-0400.jpg_Original_638989318665930000.jpg?w=3840&h=3840&bgcolor=ffffff&format=jpg', 'L', 'BLACK', 0, false, NOW(), NOW());


-- ============================================
-- 2. GIFT_PRODUCTS TABLE - 9 GRATIS GAVER
-- ============================================

-- TILGÆNGELIGE GAVER
INSERT INTO gift_products (id, name, description, image_url, stock_quantity, active, created_at, updated_at) VALUES
(1, 'Gratis Nøglering - Minimalistisk Design', 'Eksklusiv metallic nøglering med logo. Nordisk minimalistisk design i børstet stål.', 'https://www.bestman.dk/images/10021469.webp', 500, true, NOW(), NOW()),

(2, 'Gratis Bomuldstaske - Naturfarvet', 'Økologisk bomuldstaske med sort logo-tryk. Perfekt til indkøb. 38x42 cm.', 'https://www.onlineprinters.dk/$WS/diedruckerei/shopdata/media/pim/images/500x500/jpg/2c9ceb817fe031bf018022ce5ce923ec.jpg', 300, true, NOW(), NOW()),

(3, 'Gratis Strikkesokker - Vinter Edition', 'Varme uldstrikkesokker i nordisk design. Uni-størrelse (37-44). Limited stock!', 'https://rito.dk/img/cms/opskriftsbilleder/kit-rito-0046%20bambus%20sokker/20191128_170333.jpg', 150, true, NOW(), NOW()),

(4, 'Gratis Klistermærke Pack - 5 stk', 'Pakke med 5 forskellige vinyl klistermærker. Waterproof. Perfekt til laptop!', 'https://stickerland.dk/media/webp_image/catalog/product/cache/d3be0de455e072a242b4c7ad76d16818/b/l/blandede_klisterm_rker_plast_soft.webp', 800, true, NOW(), NOW()),

(5, 'Gratis Snapback Cap - Sort', 'Sort snapback kasket med broderet logo. Justerbar størrelse. Street style.', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSDsemQM_CtMwRGw2-qBZ5uwaQ2lSvbekrf2A&s', 120, true, NOW(), NOW()),

(6, 'Gratis Vand Flaske - Rustfrit Stål', '500ml vandflaske i rustfrit stål. Holder koldt i 24 timer. Lækagesikker.', 'https://grontkontor.dk/wp-content/uploads/2020/08/vandflaske-staal-valgfri-farve-01.jpg', 80, true, NOW(), NOW()),

-- UDSOLGTE GAVER (Test scenarios)
(7, 'Gratis Limited Beanie - UDSOLGT', 'Udsolgt! Varm beanie i uld-blend. Var meget populær i vinter sæsonen.', 'https://cdn.billig-arbejdstoj.dk/media/lhcfgmt4/7244029/150075_10.jpg?width=705&height=705&bgcolor=ffffff&format=webp&quality=75', 0, false, NOW(), NOW()),

(8, 'Gratis Sommerhue - Sæsonvare', 'Udgået sæsonvare. Bucket hat i beige. Kommer igen til sommer 2026.', 'https://hatshop.dk/images/zoom/hattar_garda_wynn_bucket_hat_beige_02.jpg', 0, false, NOW(), NOW()),

(9, 'Gratis Telefon Holder - UDSOLGT', 'Populær gave - midlertidigt udsolgt. Magnetisk bil telefon holder.', 'https://productimages.biltema.com/v1/Image/product/xlarge/2000042894/4', 0, false, NOW(), NOW());


-- ============================================
-- 3. GIFT_CONFIG TABLE - GAVE KONFIGURATION
-- ============================================

INSERT INTO gift_config (id, threshold_amount, active, gift_product_id, created_at, updated_at) VALUES
(1, 500.00, 1, 1, NOW(), NOW());


-- ============================================
-- 4. ADMIN TABLE - ADMINISTRATOR BRUGERE
-- ============================================

-- Admin bruger (email: admin@tshirt.dk, password: admin123)
INSERT INTO admin (id, email, name, password, created_at, updated_at) VALUES
(1, 'admin@tshirt.dk', 'Administrator', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', NOW(), NOW()),

-- Test Admin (email: test@tshirt.dk, password: test123)
(2, 'test@tshirt.dk', 'Test Administrator', '$2a$10$EblZqNptyYvcLm9VfqH.8uKEyTkKkOEhGj8FsZqnp89xqBEV/7Qe.', NOW(), NOW());


-- ============================================
-- SUMMARY
-- ============================================
-- Products created: 17 (15 active, 2 inactive)
-- Gift products created: 9 (6 active, 3 inactive)
-- Gift config: Threshold 500 kr, default gift ID 1
-- Total gift stock: 1950 units
-- Price range: 79-169 kr
-- ============================================
