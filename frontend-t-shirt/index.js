// Product data - vil blive hentet fra backend
let products = [];

// Hent produkter fra backend
async function loadProducts() {
  products = await api.getProducts();

  // Fallback data hvis backend ikke er tilgængelig
  if (!products || products.length === 0) {
    console.warn("Bruger fallback produkter - backend er ikke tilgængelig");
    products = [
      {
        id: 1,
        name: "Klassisk Grøn T-Shirt",
        description: "100% økologisk bomuld",
        price: 149,
        imageUrl:
          "https://images.unsplash.com/photo-1521572163474-6864f9cf17ab?w=400&h=400&fit=crop",
      },
      {
        id: 2,
        name: "Premium Hvid T-Shirt",
        description: "Blød og åndbar",
        price: 169,
        imageUrl:
          "https://images.unsplash.com/photo-1622445275463-afa2ab738c34?w=400&h=400&fit=crop",
      },
      {
        id: 3,
        name: "Skov Edition",
        description: "Bæredygtig produktion",
        price: 189,
        imageUrl:
          "https://images.unsplash.com/photo-1583743814966-8936f5b7be1a?w=400&h=400&fit=crop",
      },
      {
        id: 4,
        name: "Minimalistisk Design",
        description: "Tidløs stil",
        price: 159,
        imageUrl:
          "https://images.unsplash.com/photo-1618354691373-d851c5c3a990?w=400&h=400&fit=crop",
      },
      {
        id: 5,
        name: "Komfort Pasform",
        description: "Perfekt pasform",
        price: 179,
        imageUrl:
          "https://images.unsplash.com/photo-1576566588028-4147f3842f27?w=400&h=400&fit=crop",
      },
      {
        id: 6,
        name: "Natur Kollektion",
        description: "Miljøvenlig farve",
        price: 199,
        imageUrl:
          "https://images.unsplash.com/photo-1562157873-818bc0726f68?w=400&h=400&fit=crop",
      },
    ];
  }

  // Note: Removed auto-render to prevent unwanted navigation
  // Call renderSection manually after loadProducts if needed
}

// Hjælpefunktion til at formatere pris
function formatPrice(price) {
  return typeof price === "number" ? `${price} kr` : price;
}

// Hjælpefunktion til at få produkt billede
function getProductImage(product) {
  // Brug imageUrl fra backend, ellers icon, ellers placeholder
  if (product.imageUrl) {
    return `<img src="${product.imageUrl}" alt="${product.name}" style="width: 100%; height: 100%; object-fit: cover;">`;
  } else if (product.icon) {
    return product.icon;
  } else {
    return `<svg width="100%" height="100%" viewBox="0 0 100 100" fill="none" xmlns="http://www.w3.org/2000/svg">
            <!-- T-shirt outline -->
            <path d="M30 20 L25 15 L20 20 L20 35 L30 35 Z" fill="#2d5f2e" opacity="0.8"/>
            <path d="M70 20 L75 15 L80 20 L80 35 L70 35 Z" fill="#2d5f2e" opacity="0.8"/>
            <rect x="30" y="20" width="40" height="50" rx="3" fill="#2d5f2e" opacity="0.9"/>
            <rect x="35" y="25" width="30" height="40" fill="white" opacity="0.2"/>
            <circle cx="50" cy="45" r="8" fill="white" opacity="0.3"/>
        </svg>`;
  }
}

// Section templates
const sections = {
  shop: () => `
        <section class="hero">
            <div class="hero-container">
                <div class="hero-content">
                    <h1>Premium T-Shirts</h1>
                    <h2 class="hero-subtitle">Lavet til Komfort</h2>
                    <p>Opdag vores kollektion af omhyggeligt designede t-shirts lavet af de fineste materialer. Minimalistisk stil møder exceptionel kvalitet.</p>
                    <div class="hero-buttons">
                        <button class="btn-primary" onclick="renderSection('collections')">Se Kollektionen →</button>
                        <button class="btn-secondary" onclick="renderSection('about')">Læs Mere</button>
                    </div>
                </div>
                <div class="hero-image">
                    <div class="hero-image-placeholder">
                        <svg width="200" height="200" viewBox="0 0 200 200" fill="none" xmlns="http://www.w3.org/2000/svg">
                            <!-- Professional T-shirt illustration -->
                            <defs>
                                <linearGradient id="tshirtGradient" x1="0%" y1="0%" x2="0%" y2="100%">
                                    <stop offset="0%" style="stop-color:#4a8c4a;stop-opacity:1" />
                                    <stop offset="100%" style="stop-color:#2d5f2e;stop-opacity:1" />
                                </linearGradient>
                            </defs>
                            <!-- Sleeves -->
                            <path d="M60 50 L40 40 L30 50 L30 80 L60 80 Z" fill="url(#tshirtGradient)" opacity="0.9"/>
                            <path d="M140 50 L160 40 L170 50 L170 80 L140 80 Z" fill="url(#tshirtGradient)" opacity="0.9"/>
                            <!-- Body -->
                            <rect x="60" y="50" width="80" height="100" rx="5" fill="url(#tshirtGradient)"/>
                            <!-- Collar -->
                            <ellipse cx="100" cy="50" rx="15" ry="8" fill="#2d5f2e"/>
                            <!-- Design accent -->
                            <circle cx="100" cy="95" r="20" fill="white" opacity="0.2"/>
                            <path d="M85 95 L95 105 L115 85" stroke="white" stroke-width="3" opacity="0.3" fill="none" stroke-linecap="round"/>
                        </svg>
                    </div>
                </div>
            </div>
        </section>

        <section class="collections">
            <div class="section-header">
                <h2>Se Vores Kollektion</h2>
                <p>Hvert produkt er fremstillet med omhu for detaljer og lavet af premium materialer for langvarig komfort og stil.</p>
            </div>
            <div class="products-grid">
                ${products
                  .map(
                    (product) => `
                    <div class="product-card">
                        <div class="product-image">${getProductImage(
                          product
                        )}</div>
                        <div class="product-info">
                            <h3>${product.name}</h3>
                            <p>${product.description}</p>
                            <div class="product-price">
                                <span class="price">${formatPrice(
                                  product.price
                                )}</span>
                                ${product.size ? `<span style="margin-left: 0.5rem; color: #5a6a5a; font-size: 0.9rem;">• Str. ${product.size}</span>` : ''}
                            </div>
                            <div class="stock-info" style="margin-top: 0.5rem; font-size: 0.9rem;">
                                ${product.stockQuantity > 0 
                                  ? `<span style="color: #2d5f2e;">✓ ${product.stockQuantity} stk. på lager</span>` 
                                  : `<span style="color: #d32f2f;">✗ Udsolgt</span>`}
                            </div>
                            <button 
                                class="add-to-cart" 
                                onclick="addToCart(${product.id})"
                                ${product.stockQuantity <= 0 ? 'disabled style="background: #bdbdbd; cursor: not-allowed; opacity: 0.6;"' : ''}>
                                ${product.stockQuantity > 0 ? 'Tilføj' : 'Udsolgt'}
                            </button>
                        </div>
                    </div>
                `
                  )
                  .join("")}
            </div>
        </section>
    `,

  // Add this inside the const sections object in index.js
  cart: () => `
    <section class="collections" style="padding-top: 2rem;">
        <div class="section-header">
            <h2>Din Indkøbskurv</h2>
        </div>
        
        <div class="cart-container" style="max-width: 800px; margin: 0 auto; padding: 20px;">
            <div id="cart-content">
                <p style="text-align: center;">Henter din kurv...</p>
            </div>

            <div id="cart-footer" style="display: none; margin-top: 20px; text-align: right; border-top: 2px solid #eee; padding-top: 20px;">
                <h3>Total: <span id="cart-total-price">0</span> kr.</h3>
                
                <div id="gift-status-message" style="margin: 15px 0; padding: 10px; border-radius: 5px;"></div>

                <button class="btn-primary" onclick="renderSection('checkout')">Gå til kassen →</button>
            </div>
        </div>
    </section>
`,

  checkout: () => `
    <section class="checkout-section" style="padding: 3rem 2rem;">
        <div class="checkout-container" style="max-width: 1000px; margin: 0 auto;">
            <button onclick="renderSection('cart')" class="back-button" style="margin-bottom: 2rem; padding: 0.5rem 1rem; background: #f0f0f0; border: none; border-radius: 5px; cursor: pointer; display: flex; align-items: center; gap: 0.5rem;">
                ← Tilbage til kurv
            </button>
            
            <h1 style="text-align: center; color: #2d4a2d; margin-bottom: 2rem;">Checkout</h1>
            
            <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 3rem;">
                <!-- Leverings information -->
                <div class="checkout-form">
                    <h2 style="color: #2d4a2d; margin-bottom: 1.5rem;">Leveringsinformation</h2>
                    <form id="checkout-form" onsubmit="handleCheckout(event)">
                        <div class="form-group" style="margin-bottom: 1rem;">
                            <label style="display: block; margin-bottom: 0.5rem; color: #5a6a5a; font-weight: 500;">Navn</label>
                            <input type="text" name="name" required style="width: 100%; padding: 0.75rem; border: 1px solid #ddd; border-radius: 5px;" placeholder="Dit fulde navn">
                        </div>
                        
                        <div class="form-group" style="margin-bottom: 1rem;">
                            <label style="display: block; margin-bottom: 0.5rem; color: #5a6a5a; font-weight: 500;">Email</label>
                            <input type="email" name="email" required style="width: 100%; padding: 0.75rem; border: 1px solid #ddd; border-radius: 5px;" placeholder="din@email.dk">
                        </div>
                        
                        <div class="form-group" style="margin-bottom: 1rem;">
                            <label style="display: block; margin-bottom: 0.5rem; color: #5a6a5a; font-weight: 500;">Telefon</label>
                            <input type="tel" name="phone" required style="width: 100%; padding: 0.75rem; border: 1px solid #ddd; border-radius: 5px;" placeholder="12345678">
                        </div>
                        
                        <div class="form-group" style="margin-bottom: 1rem;">
                            <label style="display: block; margin-bottom: 0.5rem; color: #5a6a5a; font-weight: 500;">Adresse</label>
                            <input type="text" name="address" required style="width: 100%; padding: 0.75rem; border: 1px solid #ddd; border-radius: 5px;" placeholder="Gadenavn 123">
                        </div>
                        
                        <div style="display: grid; grid-template-columns: 1fr 2fr; gap: 1rem; margin-bottom: 1rem;">
                            <div class="form-group">
                                <label style="display: block; margin-bottom: 0.5rem; color: #5a6a5a; font-weight: 500;">Postnr.</label>
                                <input type="text" name="postalCode" required style="width: 100%; padding: 0.75rem; border: 1px solid #ddd; border-radius: 5px;" placeholder="1234">
                            </div>
                            <div class="form-group">
                                <label style="display: block; margin-bottom: 0.5rem; color: #5a6a5a; font-weight: 500;">By</label>
                                <input type="text" name="city" required style="width: 100%; padding: 0.75rem; border: 1px solid #ddd; border-radius: 5px;" placeholder="København">
                            </div>
                        </div>
                        
                        <div class="form-group" style="margin-bottom: 1.5rem;">
                            <label style="display: block; margin-bottom: 0.5rem; color: #5a6a5a; font-weight: 500;">Bemærkninger (valgfrit)</label>
                            <textarea name="notes" rows="3" style="width: 100%; padding: 0.75rem; border: 1px solid #ddd; border-radius: 5px;" placeholder="Eventuelle leveringsinstruktioner..."></textarea>
                        </div>
                        
                        <!-- TASK 1.2 & 3.2: Gift validation messages -->
                        <div id="checkout-gift-messages" style="margin-bottom: 1.5rem;"></div>
                        
                        <button type="submit" class="btn-primary" style="width: 100%; padding: 1rem; font-size: 1.1rem; font-weight: bold;">
                            Gennemfør bestilling
                        </button>
                    </form>
                </div>
                
                <!-- Ordre oversigt -->
                <div class="order-summary" style="background: #f5f9f5; padding: 2rem; border-radius: 12px; height: fit-content;">
                    <h2 style="color: #2d4a2d; margin-bottom: 1.5rem;">Ordre oversigt</h2>
                    <div id="checkout-summary">
                        <p style="text-align: center; color: #5a6a5a;">Henter ordre detaljer...</p>
                    </div>
                </div>
            </div>
        </div>
    </section>
`,

  orderConfirmation: (order) => `
    <section class="order-confirmation" style="padding: 3rem 2rem;">
        <div class="confirmation-container" style="max-width: 800px; margin: 0 auto; text-align: center;">
            <div style="background: #e8f5e9; padding: 3rem; border-radius: 16px; margin-bottom: 2rem;">
                <div style="font-size: 4rem; margin-bottom: 1rem;">✅</div>
                <h1 style="color: #2d5f2e; margin-bottom: 1rem;">Tak for din bestilling!</h1>
                <p style="color: #5a6a5a; font-size: 1.1rem;">Din ordre er bekræftet og behandles nu.</p>
            </div>
            
            <div style="background: white; padding: 2rem; border-radius: 12px; text-align: left; margin-bottom: 2rem; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
                <h2 style="color: #2d4a2d; margin-bottom: 1rem;">Ordre detaljer</h2>
                <div style="border-bottom: 1px solid #eee; padding: 0.75rem 0;">
                    <strong>Ordre nummer:</strong> #${order.orderId}
                </div>
                <div style="border-bottom: 1px solid #eee; padding: 0.75rem 0;">
                    <strong>Navn:</strong> ${order.customerName}
                </div>
                <div style="border-bottom: 1px solid #eee; padding: 0.75rem 0;">
                    <strong>Email:</strong> ${order.customerEmail}
                </div>
                <div style="border-bottom: 1px solid #eee; padding: 0.75rem 0;">
                    <strong>Leveringsadresse:</strong> ${order.deliveryAddress}
                </div>
                
                <h3 style="color: #2d4a2d; margin-top: 1.5rem; margin-bottom: 1rem;">Produkter</h3>
                ${order.items.map(item => `
                    <div style="display: flex; justify-content: space-between; padding: 0.5rem 0; border-bottom: 1px solid #f0f0f0;">
                        <span>${item.productName} × ${item.quantity}</span>
                        <span style="font-weight: bold;">${item.rowTotal} kr.</span>
                    </div>
                `).join('')}
                
                <!-- TASK 2.3: Show gift on confirmation -->
                ${order.freeGift ? `
                    <div style="display: flex; justify-content: space-between; padding: 0.75rem 0; background: #e8f5e9; margin-top: 0.5rem; padding: 1rem; border-radius: 8px;">
                        <span style="color: #2d5f2e; font-weight: bold;">🎁 Gratis gave: ${order.freeGift.productName}</span>
                        <span style="color: #2d5f2e; font-weight: bold;">GRATIS</span>
                    </div>
                ` : ''}
                
                <div style="display: flex; justify-content: space-between; padding: 1rem 0; margin-top: 1rem; border-top: 2px solid #2d5f2e; font-size: 1.2rem; font-weight: bold; color: #2d5f2e;">
                    <span>Total:</span>
                    <span>${order.totalAmount} kr.</span>
                </div>
            </div>
            
            <div style="background: #fff3cd; padding: 1.5rem; border-radius: 8px; border-left: 4px solid #ffc107; margin-bottom: 2rem; text-align: left;">
                <p style="margin: 0; color: #856404;">
                    📧 En ordrebekræftelse er sendt til <strong>${order.customerEmail}</strong>
                </p>
            </div>
            
            <button onclick="renderSection('shop')" class="btn-primary" style="padding: 1rem 2rem; font-size: 1.1rem;">
                Fortsæt med at handle
            </button>
        </div>
    </section>
`,

  gifts: () => `
        <section class="collections" style="padding-top: 2rem;">
            <div class="section-header">
                <h2>Vælg din Gratis Gave</h2>
                <p>Som tak for din bestilling kan du vælge en af følgende gaver.</p>
            </div>
            <div id="gift-container" class="products-grid">
                <p style="text-align:center; width:100%;">Indlæser gaver...</p>
            </div>
        </section>
    `,

  collections: () => `
        <section class="collections" style="padding-top: 2rem;">
            <div class="section-header">
                <h2>Vores T-Shirt Kollektioner</h2>
                <p>Udforsk vores nøje udvalgte kollektioner af premium t-shirts. Hver kollektion er designet med fokus på kvalitet, komfort og stil.</p>
            </div>
            <div class="products-grid">
                ${products
                  .map(
                    (product) => `
                    <div class="product-card" onclick="viewProductDetail(${product.id})" style="cursor: pointer;">
                        <div class="product-image">${getProductImage(
                          product
                        )}</div>
                        <div class="product-info">
                            <h3>${product.name}</h3>
                            <p>${product.description}</p>
                            <div class="product-price">
                                <span class="price">${formatPrice(
                                  product.price
                                )}</span>
                                ${product.size ? `<span style="margin-left: 0.5rem; color: #5a6a5a; font-size: 0.9rem;">• Str. ${product.size}</span>` : ''}
                            </div>
                            <div class="stock-info" style="margin-top: 0.5rem; font-size: 0.9rem;">
                                ${product.stockQuantity > 0 
                                  ? `<span style="color: #2d5f2e;">✓ ${product.stockQuantity} på lager</span>` 
                                  : `<span style="color: #d32f2f;">✗ Udsolgt</span>`}
                            </div>
                        </div>
                    </div>
                `
                  )
                  .join("")}
            </div>
        </section>
    `,

  productDetail: (product) => `
        <section class="product-detail" style="padding: 3rem 2rem;">
            <div class="product-detail-container" style="max-width: 1200px; margin: 0 auto;">
                <button onclick="renderSection('collections')" class="back-button" style="margin-bottom: 2rem; padding: 0.5rem 1rem; background: #f0f0f0; border: none; border-radius: 5px; cursor: pointer; display: flex; align-items: center; gap: 0.5rem;">
                    ← Tilbage til kollektioner
                </button>
                
                <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 3rem; align-items: start;">
                    <div class="product-detail-image" style="background: linear-gradient(135deg, #e8f5e9 0%, #d4e4d4 100%); border-radius: 16px; padding: 2rem; display: flex; align-items: center; justify-content: center; min-height: 500px;">
                        ${getProductImage(product)}
                    </div>
                    
                    <div class="product-detail-info">
                        <h1 style="font-size: 2.5rem; color: #2d4a2d; margin-bottom: 1rem;">${product.name}</h1>
                        <p style="font-size: 1.2rem; color: #5a6a5a; margin-bottom: 2rem; line-height: 1.6;">${product.description}</p>
                        
                        <div style="background: #f5f9f5; padding: 1.5rem; border-radius: 12px; margin-bottom: 2rem;">
                            <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 1rem;">
                                <span style="font-size: 1.1rem; color: #5a6a5a;">Pris:</span>
                                <span style="font-size: 2.5rem; color: #2d5f2e; font-weight: bold;">${formatPrice(product.price)}</span>
                            </div>
                        </div>
                        
                        <div style="margin-bottom: 2rem;">
                            <h3 style="color: #2d4a2d; margin-bottom: 1rem;">Produkt detaljer</h3>
                            <ul style="list-style: none; padding: 0;">
                                <li style="padding: 0.5rem 0; border-bottom: 1px solid #e0e0e0; display: flex; justify-content: space-between;">
                                    <span style="color: #5a6a5a;">Størrelse:</span>
                                    <span style="font-weight: 500;">${product.size || 'One Size'}</span>
                                </li>
                                <li style="padding: 0.5rem 0; border-bottom: 1px solid #e0e0e0; display: flex; justify-content: space-between;">
                                    <span style="color: #5a6a5a;">Materiale:</span>
                                    <span style="font-weight: 500;">100% økologisk bomuld</span>
                                </li>
                                <li style="padding: 0.5rem 0; border-bottom: 1px solid #e0e0e0; display: flex; justify-content: space-between;">
                                    <span style="color: #5a6a5a;">Pasform:</span>
                                    <span style="font-weight: 500;">Regular fit</span>
                                </li>
                                <li style="padding: 0.5rem 0; border-bottom: 1px solid #e0e0e0; display: flex; justify-content: space-between;">
                                    <span style="color: #5a6a5a;">Pleje:</span>
                                    <span style="font-weight: 500;">Maskinvask 30°</span>
                                </li>
                                <li style="padding: 0.5rem 0; border-bottom: 1px solid #e0e0e0; display: flex; justify-content: space-between;">
                                    <span style="color: #5a6a5a;">Lagerstatus:</span>
                                    <span style="font-weight: 500; ${product.stockQuantity > 0 ? 'color: #2d5f2e;' : 'color: #d32f2f;'}">
                                        ${product.stockQuantity > 0 ? `✓ ${product.stockQuantity} stk. på lager` : '✗ Udsolgt'}
                                    </span>
                                </li>
                                <li style="padding: 0.5rem 0; display: flex; justify-content: space-between;">
                                    <span style="color: #5a6a5a;">Produkt ID:</span>
                                    <span style="font-weight: 500;">#${product.id}</span>
                                </li>
                            </ul>
                        </div>
                        
                        <button 
                            onclick="${product.stockQuantity > 0 ? `addToCartFromDetail(${product.id})` : 'return false;'}" 
                            ${product.stockQuantity <= 0 ? 'disabled' : ''}
                            class="add-to-cart-large" 
                            style="width: 100%; padding: 1.2rem 2rem; 
                                ${product.stockQuantity > 0 
                                    ? 'background: linear-gradient(135deg, #2d5f2e 0%, #4a8c4a 100%); cursor: pointer; box-shadow: 0 4px 12px rgba(45, 95, 46, 0.3);' 
                                    : 'background: #bdbdbd; cursor: not-allowed; opacity: 0.6; filter: blur(0.5px);'}
                                color: white; border: none; border-radius: 12px; font-size: 1.2rem; font-weight: bold; transition: all 0.3s;">
                            ${product.stockQuantity > 0 ? '🛒 Tilføj til kurv' : '✗ Udsolgt'}
                        </button>
                        
                        <div style="margin-top: 2rem; padding: 1rem; background: #e8f5e9; border-radius: 8px; border-left: 4px solid #2d5f2e;">
                            <p style="margin: 0; color: #2d5f2e; font-weight: 500;">✓ Gratis fragt over 299 kr.</p>
                            <p style="margin: 0.5rem 0 0 0; color: #2d5f2e; font-weight: 500;">✓ 30 dages returret</p>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    `,

  about: () => `
        <section class="about">
            <div class="about-container">
                <h2>Om BilligT-Shirt</h2>
                <p>Vi tror på, at kvalitet ikke behøver at koste en formue. Vores mission er at levere premium t-shirts til en pris, der er fair for alle.</p>
                <p>Alle vores produkter er lavet af de fineste materialer og produceret på en bæredygtig måde.</p>
                
                <div class="features">
                    <div class="feature">
                        <div class="feature-icon">
                            <svg width="60" height="60" viewBox="0 0 60 60" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <!-- Leaf icon for organic -->
                                <path d="M30 10 Q15 15 15 30 Q15 45 30 50 Q30 45 30 30 Z" fill="#2d5f2e" opacity="0.7"/>
                                <path d="M30 10 Q45 15 45 30 Q45 45 30 50" fill="#4a8c4a" opacity="0.5"/>
                                <path d="M30 15 Q30 25 30 50" stroke="#2d5f2e" stroke-width="2"/>
                                <circle cx="30" cy="30" r="18" stroke="#2d5f2e" stroke-width="2" fill="none" opacity="0.5"/>
                            </svg>
                        </div>
                        <h3>100% Økologisk</h3>
                        <p>Alle vores t-shirts er lavet af certificeret økologisk bomuld</p>
                    </div>
                    <div class="feature">
                        <div class="feature-icon">
                            <svg width="60" height="60" viewBox="0 0 60 60" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <!-- Recycle icon -->
                                <path d="M30 15 L25 25 L35 25 Z" fill="#2d5f2e"/>
                                <path d="M20 40 L15 30 L25 35 Z" fill="#2d5f2e"/>
                                <path d="M40 40 L45 30 L35 35 Z" fill="#2d5f2e"/>
                                <path d="M30 20 A12 12 0 0 1 40 35" stroke="#4a8c4a" stroke-width="3" fill="none" stroke-linecap="round"/>
                                <path d="M20 35 A12 12 0 0 1 25 20" stroke="#4a8c4a" stroke-width="3" fill="none" stroke-linecap="round"/>
                                <path d="M35 35 A12 12 0 0 1 30 20" stroke="#4a8c4a" stroke-width="3" fill="none" stroke-linecap="round"/>
                            </svg>
                        </div>
                        <h3>Bæredygtig</h3>
                        <p>Miljøvenlig produktion og fair handel</p>
                    </div>
                    <div class="feature">
                        <div class="feature-icon">
                            <svg width="60" height="60" viewBox="0 0 60 60" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <!-- Award/medal icon -->
                                <circle cx="30" cy="25" r="12" fill="#4a8c4a" opacity="0.7"/>
                                <circle cx="30" cy="25" r="12" stroke="#2d5f2e" stroke-width="2" fill="none"/>
                                <path d="M22 30 L18 45 L24 42 L30 48 L30 37" fill="#2d5f2e" opacity="0.6"/>
                                <path d="M38 30 L42 45 L36 42 L30 48 L30 37" fill="#2d5f2e" opacity="0.6"/>
                                <text x="30" y="30" text-anchor="middle" fill="white" font-size="16" font-weight="bold">★</text>
                            </svg>
                        </div>
                        <h3>Premium Kvalitet</h3>
                        <p>Holdbare materialer der holder til daglig brug</p>
                    </div>
                    <div class="feature">
                        <div class="feature-icon">
                            <svg width="60" height="60" viewBox="0 0 60 60" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <!-- Price tag icon -->
                                <path d="M15 15 L15 30 L30 45 L45 30 L30 15 Z" fill="#4a8c4a" opacity="0.6"/>
                                <path d="M15 15 L15 30 L30 45 L45 30 L30 15 Z" stroke="#2d5f2e" stroke-width="2" fill="none"/>
                                <circle cx="22" cy="22" r="3" fill="white"/>
                                <text x="30" y="32" text-anchor="middle" fill="white" font-size="14" font-weight="bold">kr</text>
                            </svg>
                        </div>
                        <h3>Fair Priser</h3>
                        <p>Høj kvalitet til en pris alle har råd til</p>
                    </div>
                </div>
            </div>
        </section>
    `,

  login: () => `
        <section class="auth-section">
            <div class="auth-container">
                <div class="auth-card">
                    <div class="auth-header">
                        <div class="auth-icon">
                            <svg width="60" height="60" viewBox="0 0 60 60" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <!-- Professional user icon -->
                                <circle cx="30" cy="30" r="26" fill="#e8f5e9"/>
                                <circle cx="30" cy="30" r="26" stroke="#2d5f2e" stroke-width="2"/>
                                <circle cx="30" cy="23" r="8" fill="#2d5f2e"/>
                                <path d="M15 46C15 38 21 33 30 33C39 33 45 38 45 46" stroke="#2d5f2e" stroke-width="2.5" stroke-linecap="round"/>
                            </svg>
                        </div>
                        <h2>Log ind</h2>
                        <p>Velkommen tilbage til BilligT-Shirt</p>
                    </div>
                    <form class="auth-form" onsubmit="handleLogin(event)">
                        <div class="form-group">
                            <label for="login-email">Email</label>
                            <input type="email" id="login-email" name="email" placeholder="din@email.dk" required>
                        </div>
                        <div class="form-group">
                            <label for="login-password">Adgangskode</label>
                            <input type="password" id="login-password" name="password" placeholder="••••••••" required>
                        </div>
                        <div class="form-options">
                            <label class="checkbox-label">
                                <input type="checkbox" name="remember">
                                <span>Husk mig</span>
                            </label>
                            <a href="#" class="link">Glemt adgangskode?</a>
                        </div>
                        <button type="submit" class="btn-primary btn-full">Log ind</button>
                    </form>
                    <div class="auth-footer">
                        <p>Har du ikke en konto? <a href="#" onclick="renderSection('signup'); return false;" class="link">Tilmeld dig her</a></p>
                    </div>
                </div>
            </div>
        </section>
    `,

  signup: () => `
        <section class="auth-section">
            <div class="auth-container">
                <div class="auth-card">
                    <div class="auth-header">
                        <div class="auth-icon">
                            <svg width="60" height="60" viewBox="0 0 60 60" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <!-- User plus icon -->
                                <circle cx="25" cy="30" r="24" fill="#e8f5e9"/>
                                <circle cx="25" cy="30" r="24" stroke="#2d5f2e" stroke-width="2"/>
                                <circle cx="25" cy="23" r="7" fill="#2d5f2e"/>
                                <path d="M12 42C12 35 17 31 25 31C33 31 38 35 38 42" stroke="#2d5f2e" stroke-width="2.5" stroke-linecap="round"/>
                                <!-- Plus sign -->
                                <circle cx="45" cy="20" r="10" fill="#4a8c4a"/>
                                <path d="M45 15 L45 25 M40 20 L50 20" stroke="white" stroke-width="2.5" stroke-linecap="round"/>
                            </svg>
                        </div>
                        <h2>Opret konto</h2>
                        <p>Bliv en del af BilligT-Shirt familien</p>
                    </div>
                    <form class="auth-form" onsubmit="handleSignup(event)">
                        <div class="form-row">
                            <div class="form-group">
                                <label for="signup-firstname">Fornavn</label>
                                <input type="text" id="signup-firstname" name="firstname" placeholder="Dit fornavn" required>
                            </div>
                            <div class="form-group">
                                <label for="signup-lastname">Efternavn</label>
                                <input type="text" id="signup-lastname" name="lastname" placeholder="Dit efternavn" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="signup-email">Email</label>
                            <input type="email" id="signup-email" name="email" placeholder="din@email.dk" required>
                        </div>
                        <div class="form-group">
                            <label for="signup-password">Adgangskode</label>
                            <input type="password" id="signup-password" name="password" placeholder="Minimum 8 tegn" required minlength="8">
                        </div>
                        <div class="form-group">
                            <label for="signup-confirm-password">Bekræft adgangskode</label>
                            <input type="password" id="signup-confirm-password" name="confirmPassword" placeholder="Gentag adgangskode" required>
                        </div>
                        <div class="form-options">
                            <label class="checkbox-label">
                                <input type="checkbox" name="terms" required>
                                <span>Jeg accepterer <a href="#" class="link">vilkår og betingelser</a></span>
                            </label>
                        </div>
                        <button type="submit" class="btn-primary btn-full">Opret konto</button>
                    </form>
                    <div class="auth-footer">
                        <p>Har du allerede en konto? <a href="#" onclick="renderSection('login'); return false;" class="link">Log ind her</a></p>
                    </div>
                </div>
            </div>
        </section>
    `,
};

// Render function
function renderSection(sectionName) {
  const content = document.getElementById("app-content");
  
  // Handle orderConfirmation specially
  if (sectionName === 'orderConfirmation' && window.currentOrder) {
    content.innerHTML = sections.orderConfirmation(window.currentOrder);
  } else if (sections[sectionName]) {
    content.innerHTML = sections[sectionName]();
  }

  // If the user navigated to 'gifts', we need to fetch the data
  if (sectionName === "gifts") {
    loadGiftProducts();
  }

  if (sectionName === "cart") {
    loadCart(); // Triggers the function we just added
  }
  
  if (sectionName === "checkout") {
    loadCheckoutSummary();
  }

  // Update active nav link
  document.querySelectorAll(".nav-menu a").forEach((link) => {
    link.style.color =
      link.dataset.section === sectionName ? "#4a8c4a" : "#2d4a2d";
  });
  
  // Scroll to top when navigating to a new section
  window.scrollTo(0, 0);
}

// Get current section from URL or default
function getCurrentSection() {
  const path = window.location.pathname;
  if (path.includes("collections")) return "collections";
  if (path.includes("about")) return "about";
  if (path.includes("login")) return "login";
  if (path.includes("signup")) return "signup";
  return "shop";
}

// View product detail page
function viewProductDetail(productId) {
  const product = products.find((p) => p.id === productId);
  if (!product) {
    alert('Produkt ikke fundet');
    return;
  }
  
  const content = document.getElementById("app-content");
  content.innerHTML = sections.productDetail(product);
}

// Add to cart from detail page
async function addToCartFromDetail(productId) {
  await addToCart(productId);
  // Update cart counter after adding
  updateCartCounter();
}

// Add to cart function - nu med backend integration
async function addToCart(productId) {
  const product = products.find((p) => p.id === productId);
  
  if (!product) return;
  
  // Check if out of stock
  if (product.stockQuantity <= 0) {
    console.log('Produkt er udsolgt');
    return;
  }
  
  // Check current quantity in cart
  try {
    const cartData = await api.getCart();
    const existingItem = cartData.items?.find(item => {
      const itemId = item.productId || item.id;
      return itemId === productId;
    });
    
    const currentQuantityInCart = existingItem ? existingItem.quantity : 0;
    
    // Check if adding one more would exceed stock
    if (currentQuantityInCart >= product.stockQuantity) {
      console.log(`Kan ikke tilføje flere - kun ${product.stockQuantity} på lager`);
      return;
    }
  } catch (error) {
    console.error('Fejl ved check af lager:', error);
  }

  // Prøv at tilføje til backend kurv
  const result = await api.addToCart(productId, 1);

  if (result) {
    // Update cart counter
    updateCartCounter();
    // Refresh products to get updated stock
    await loadProducts();
  } else {
    // Fallback hvis backend ikke virker
    updateCartCounter();
  }
}

// ==========================================
// CART FUNCTIONALITY (Tasks 1.3 & 1.4)
// ==========================================

// TASK 1.3: Fetch Cart Data from Backend
async function loadCart() {
    const content = document.getElementById('cart-content');
    const footer = document.getElementById('cart-footer');
    
    if (!content) return;

    try {
        // HER ER RETTELSEN: Vi tilføjer options-objektet med credentials
        const response = await fetch('http://localhost:8080/api/cart', {
            credentials: 'include'  // <--- VIGTIGT! Husk mig!
        }); 
        
        if (!response.ok) throw new Error('Kunne ikke hente kurv');
        
        const cartData = await response.json();
        renderCartItems(cartData, content, footer);
        
        // TASK 1.2: Auto-trigger popup when threshold is reached
        // Check if user can select a gift and hasn't selected one yet
        if (cartData.canSelectFreeGift === true && !cartData.hasFreeGift) {
            // Small delay to ensure cart is rendered first
            setTimeout(() => {
                showGiftSelectionModal();
            }, 500);
        }
        
    } catch (error) {
        console.error("Cart error:", error);
        content.innerHTML = `<p style="text-align:center; color:red;">Fejl: ${error.message}</p>`;
    }
}

// TASK 1.4: Convert JSON to HTML
function renderCartItems(cart, contentElement, footerElement) {
    // 1. Check if cart is empty
    if (!cart.items || cart.items.length === 0) {
        contentElement.innerHTML = '<p style="text-align:center; font-size: 1.2rem;">Din kurv er tom 🛒</p>';
        footerElement.style.display = 'none';
        return;
    }

    // 2. Build HTML Table
    let html = `
        <table style="width: 100%; border-collapse: collapse; margin-bottom: 20px;">
            <thead>
                <tr style="border-bottom: 2px solid #eee; text-align: left; color: #2d5f2e;">
                    <th style="padding: 10px;">Produkt</th>
                    <th style="padding: 10px;">Antal</th>
                    <th style="padding: 10px;">Pris</th>
                    <th style="padding: 10px;">I alt</th>
                    <th style="padding: 10px; text-align: center;">Handling</th>
                </tr>
            </thead>
            <tbody>
    `;

    // 3. Loop through the "items" list from your Java DTO
    cart.items.forEach(item => {
        // Use either item.productId or item.id depending on backend DTO
        const itemId = item.productId || item.id;
        
        html += `
            <tr style="border-bottom: 1px solid #eee;">
                <td style="padding: 15px 10px;">
                    <strong>${item.productName}</strong>
                </td>
                <td style="padding: 15px 10px;">
                    <div style="display: flex; align-items: center; gap: 8px;">
                        <button onclick="decreaseQuantity(${itemId}, ${item.quantity})" 
                                class="quantity-btn" 
                                style="width: 30px; height: 30px; border: 1px solid #ddd; background: white; border-radius: 5px; cursor: pointer; font-weight: bold; color: #2d5f2e;">
                            −
                        </button>
                        <input type="number" 
                               value="${item.quantity}" 
                               min="0" 
                               max="99"
                               onchange="updateQuantity(${itemId}, this.value)"
                               style="width: 60px; text-align: center; padding: 5px; border: 1px solid #ddd; border-radius: 5px; font-weight: bold;">
                        <button onclick="increaseQuantity(${itemId}, ${item.quantity})" 
                                class="quantity-btn"
                                style="width: 30px; height: 30px; border: 1px solid #ddd; background: white; border-radius: 5px; cursor: pointer; font-weight: bold; color: #2d5f2e;">
                            +
                        </button>
                    </div>
                </td>
                <td style="padding: 15px 10px;">
                    ${item.pricePerUnit} kr.
                </td>
                <td style="padding: 15px 10px; font-weight: bold;">
                    ${item.rowTotal} kr.
                </td>
                <td style="padding: 15px 10px; text-align: center;">
                    <button onclick="removeFromCart(${itemId}, '${item.productName}')" 
                            class="remove-btn"
                            style="background: #d32f2f; color: white; border: none; padding: 8px 16px; border-radius: 5px; cursor: pointer; font-weight: 500; transition: all 0.3s;">
                        🗑️ Fjern
                    </button>
                </td>
            </tr>
        `;
    });

    // TASK 3.2: Add free gift row if present
    if (cart.freeGift) {
        html += `
            <tr style="border-bottom: 1px solid #eee; background-color: #e8f5e9;">
                <td style="padding: 15px 10px;">
                    <strong>🎁 ${cart.freeGift.productName}</strong>
                    <span style="display: block; color: #2d5f2e; font-size: 0.9rem; font-weight: bold;">Gratis gave</span>
                </td>
                <td style="padding: 15px 10px;">
                    1 stk.
                </td>
                <td style="padding: 15px 10px; color: #2d5f2e;">
                    GRATIS
                </td>
                <td style="padding: 15px 10px; font-weight: bold; color: #2d5f2e;">
                    0 kr.
                </td>
            </tr>
        `;
    }

    html += '</tbody></table>';
    contentElement.innerHTML = html;

    // 4. Update Footer (Grand Total)
    document.getElementById('cart-total-price').textContent = cart.grandTotal;
    footerElement.style.display = 'block';

    // --- NYT: Opdater statusboksen baseret på tallene ---
    updateGiftStatus(cart);
}

// ==========================================
// CART COUNTER FUNCTIONALITY
// ==========================================

// Update cart counter badge
async function updateCartCounter() {
    try {
        const response = await fetch(`${API_BASE_URL}/cart`, {
            credentials: 'include'
        });
        
        if (!response.ok) {
            console.log('Kunne ikke hente kurv for counter');
            return;
        }
        
        const cartData = await response.json();
        const counterElement = document.getElementById('cart-counter');
        
        if (!counterElement) return;
        
        // Calculate total number of items in cart
        let totalItems = 0;
        if (cartData.items && cartData.items.length > 0) {
            totalItems = cartData.items.reduce((sum, item) => sum + item.quantity, 0);
        }
        
        // Update counter display
        if (totalItems > 0) {
            counterElement.textContent = totalItems;
            counterElement.style.display = 'flex';
            
            // Add bounce animation
            counterElement.classList.remove('updated');
            void counterElement.offsetWidth; // Trigger reflow
            counterElement.classList.add('updated');
        } else {
            counterElement.style.display = 'none';
        }
        
    } catch (error) {
        console.error('Fejl ved opdatering af cart counter:', error);
    }
}

// ==========================================
// CART ITEM MANAGEMENT
// ==========================================

// Increase quantity
async function increaseQuantity(productId, currentQuantity) {
    const product = products.find(p => p.id === productId);
    
    if (!product) {
        console.error('Produkt ikke fundet');
        return;
    }
    
    // Check if we can increase
    if (currentQuantity >= product.stockQuantity) {
        alert(`Der er kun ${product.stockQuantity} stk. på lager af dette produkt.`);
        return;
    }
    
    const newQuantity = currentQuantity + 1;
    await updateQuantity(productId, newQuantity);
}

// Decrease quantity
async function decreaseQuantity(productId, currentQuantity) {
    const newQuantity = currentQuantity - 1;
    
    if (newQuantity === 0) {
        // Ask for confirmation when going to 0
        const productName = getProductNameFromCart(productId);
        if (confirm(`Er du sikker på at du vil fjerne "${productName}" fra kurven?`)) {
            await updateQuantity(productId, 0);
        }
    } else {
        await updateQuantity(productId, newQuantity);
    }
}

// Update quantity (main function)
async function updateQuantity(productId, newQuantity) {
    const quantity = parseInt(newQuantity);
    
    // Validate quantity
    if (isNaN(quantity) || quantity < 0) {
        alert('Ugyldig antal. Venligst indtast et positivt tal.');
        await loadCart(); // Reload to reset
        return;
    }
    
    // Check stock availability
    const product = products.find(p => p.id === productId);
    if (product && quantity > product.stockQuantity) {
        alert(`Der er kun ${product.stockQuantity} stk. på lager af dette produkt.`);
        await loadCart(); // Reload to reset
        return;
    }
    
    // If quantity is 0, confirm removal
    if (quantity === 0) {
        const productName = getProductNameFromCart(productId);
        if (!confirm(`Er du sikker på at du vil fjerne "${productName}" fra kurven?`)) {
            await loadCart(); // Reload to reset
            return;
        }
    }
    
    try {
        // Call API to update quantity
        const result = await api.updateCartItemQuantity(productId, quantity);
        
        if (result) {
            // Reload cart to show updated data
            await loadCart();
            // Update counter
            updateCartCounter();
            
            if (quantity === 0) {
                console.log('Produkt fjernet fra kurven');
            } else {
                console.log(`Antal opdateret til ${quantity}`);
            }
        } else {
            alert('Kunne ikke opdatere kurven. Prøv igen.');
            await loadCart(); // Reload to reset
        }
    } catch (error) {
        console.error('Fejl ved opdatering af antal:', error);
        alert('Der opstod en fejl. Prøv igen.');
        await loadCart(); // Reload to reset
    }
}

// Remove item from cart with confirmation
async function removeFromCart(productId, productName) {
    // Double confirmation
    if (!confirm(`Er du sikker på at du vil fjerne "${productName}" fra kurven?`)) {
        return;
    }
    
    try {
        const result = await api.removeCartItem(productId);
        
        if (result) {
            // Reload cart
            await loadCart();
            // Update counter
            updateCartCounter();
            
            console.log(`${productName} fjernet fra kurven`);
        } else {
            alert('Kunne ikke fjerne produktet. Prøv igen.');
        }
    } catch (error) {
        console.error('Fejl ved fjernelse af produkt:', error);
        alert('Der opstod en fejl. Prøv igen.');
    }
}

// Helper function to get product name from current cart display
function getProductNameFromCart(productId) {
    // Try to find the product name in the current cart display
    const rows = document.querySelectorAll('.cart-container table tbody tr');
    for (let row of rows) {
        const removeBtn = row.querySelector(`button[onclick*="${productId}"]`);
        if (removeBtn) {
            const productNameElement = row.querySelector('strong');
            if (productNameElement) {
                return productNameElement.textContent;
            }
        }
    }
    return 'dette produkt';
}

// ==========================================
// CHECKOUT FUNCTIONALITY
// ==========================================

// Load checkout summary
async function loadCheckoutSummary() {
    const summaryElement = document.getElementById('checkout-summary');
    const messagesElement = document.getElementById('checkout-gift-messages');
    
    if (!summaryElement) return;
    
    try {
        const response = await fetch(`${API_BASE_URL}/cart`, {
            credentials: 'include'
        });
        
        if (!response.ok) {
            summaryElement.innerHTML = '<p style="color: red;">Kunne ikke hente kurv. Prøv igen.</p>';
            return;
        }
        
        const cart = await response.json();
        
        if (!cart.items || cart.items.length === 0) {
            summaryElement.innerHTML = '<p>Din kurv er tom.</p>';
            return;
        }
        
        // Build summary HTML
        let html = '<div style="margin-bottom: 1rem;">';
        
        cart.items.forEach(item => {
            html += `
                <div style="display: flex; justify-content: space-between; padding: 0.75rem 0; border-bottom: 1px solid #ddd;">
                    <div>
                        <strong>${item.productName}</strong>
                        <div style="color: #5a6a5a; font-size: 0.9rem;">${item.quantity} × ${item.pricePerUnit} kr.</div>
                    </div>
                    <strong>${item.rowTotal} kr.</strong>
                </div>
            `;
        });
        
        // Show free gift if present
        if (cart.freeGift) {
            html += `
                <div style="display: flex; justify-content: space-between; padding: 0.75rem; background: #e8f5e9; border-radius: 8px; margin-top: 0.5rem;">
                    <div>
                        <strong style="color: #2d5f2e;">🎁 ${cart.freeGift.productName}</strong>
                        <div style="color: #2d5f2e; font-size: 0.9rem;">Gratis gave</div>
                    </div>
                    <strong style="color: #2d5f2e;">GRATIS</strong>
                </div>
            `;
        }
        
        html += `
            <div style="display: flex; justify-content: space-between; padding: 1rem 0; margin-top: 1rem; border-top: 2px solid #2d5f2e; font-size: 1.3rem; font-weight: bold; color: #2d5f2e;">
                <span>Total:</span>
                <span>${cart.grandTotal} kr.</span>
            </div>
        </div>`;
        
        // Add shipping info
        html += `
            <div style="background: #e8f5e9; padding: 1rem; border-radius: 8px; margin-top: 1rem;">
                <p style="margin: 0; color: #2d5f2e; font-weight: 500;">✓ ${cart.grandTotal >= 299 ? 'Gratis fragt!' : 'Fragt: 49 kr.'}</p>
                <p style="margin: 0.5rem 0 0 0; color: #2d5f2e; font-weight: 500;">✓ Forventet levering: 2-4 hverdage</p>
            </div>
        `;
        
        summaryElement.innerHTML = html;
        
        // TASK 1.2: Show gift warning if applicable
        if (cart.canSelectFreeGift && !cart.freeGift) {
            messagesElement.innerHTML = `
                <div style="background: #fff3cd; padding: 1rem; border-radius: 8px; border-left: 4px solid #ffc107;">
                    <strong>⚠️ Husk din gratis gave!</strong>
                    <p style="margin: 0.5rem 0 0 0;">Du har optjent en gratis gave, men har ikke valgt en endnu.</p>
                    <button onclick="renderSection('cart')" style="margin-top: 0.5rem; padding: 0.5rem 1rem; background: #2d5f2e; color: white; border: none; border-radius: 5px; cursor: pointer;">
                        Tilbage til kurv for at vælge gave
                    </button>
                </div>
            `;
        }
        
    } catch (error) {
        console.error('Fejl ved indlæsning af checkout:', error);
        summaryElement.innerHTML = '<p style="color: red;">Der opstod en fejl. Prøv igen.</p>';
    }
}

// Handle checkout form submission
async function handleCheckout(event) {
    event.preventDefault();
    
    const formData = new FormData(event.target);
    const submitButton = event.target.querySelector('button[type="submit"]');
    const messagesElement = document.getElementById('checkout-gift-messages');
    
    // Disable button
    submitButton.disabled = true;
    submitButton.textContent = 'Behandler...';
    
    try {
        // DEBUG: Tjek session cookie
        console.log('🍪 Session cookie:', document.cookie);
        
        // DEBUG: Hent cart først for at verificere
        const cartCheck = await api.getCart();
        console.log('📦 Cart før checkout:', cartCheck);
        
        if (!cartCheck || !cartCheck.items || cartCheck.items.length === 0) {
            throw new Error('Kurven er tom. Tilføj produkter før checkout.');
        }
        
        const orderData = {
            customerName: formData.get('name'),
            customerEmail: formData.get('email'),
            customerPhone: formData.get('phone'),
            deliveryAddress: `${formData.get('address')}, ${formData.get('postalCode')} ${formData.get('city')}`, // Vi bruger det nye navn
            notes: formData.get('notes') || ''
        };
        
        console.log('📤 Sender ordre data:', orderData);
        
        // Call API to place order
        const result = await api.placeOrder(orderData);
        
        if (result && result.orderId) {
            // Store order result for confirmation page
            window.currentOrder = result;
            
            // Refresh products to update stock quantities (without re-rendering)
            await loadProducts();
            
            // Navigate to order confirmation - MUST be after loadProducts
            renderSection('orderConfirmation');
            
            // Clear cart counter
            updateCartCounter();
            
        } else if (result && result.giftRemoved) {
            // TASK 3.2: Show message if gift was removed
            messagesElement.innerHTML = `
                <div style="background: #fff3cd; padding: 1.5rem; border-radius: 8px; border-left: 4px solid #ffc107; margin-bottom: 1rem;">
                    <strong>⚠️ Din gratis gave er ikke længere tilgængelig</strong>
                    <p style="margin: 0.5rem 0 0 0;">Desværre er "${result.removedGiftName}" udsolgt. Din ordre er blevet opdateret uden gaven.</p>
                    <button onclick="handleCheckout(event)" style="margin-top: 0.5rem; padding: 0.5rem 1rem; background: #2d5f2e; color: white; border: none; border-radius: 5px; cursor: pointer;">
                        Fortsæt uden gave
                    </button>
                </div>
            `;
            submitButton.disabled = false;
            submitButton.textContent = 'Gennemfør bestilling';
        } else {
            throw new Error('Ukendt fejl');
        }
        
    } catch (error) {
        console.error('Checkout fejl:', error);
        
        // TASK 1.2: Show error message
        messagesElement.innerHTML = `
            <div style="background: #ffebee; padding: 1rem; border-radius: 8px; border-left: 4px solid #d32f2f;">
                <strong>❌ Fejl ved gennemførelse af ordre</strong>
                <p style="margin: 0.5rem 0 0 0;">${error.message || 'Der opstod en fejl. Prøv venligst igen.'}</p>
            </div>
        `;
        
        submitButton.disabled = false;
        submitButton.textContent = 'Gennemfør bestilling';
    }
}

// ==========================================
// AUTHENTICATION HANDLERS
// ==========================================

// Login handler - OPDATERET MED VARIABLER
async function handleLogin(event) {
    event.preventDefault();
    const formData = new FormData(event.target);
    
    // HENT VARIABLER FRA FORM (Dette manglede!)
    const email = formData.get("email");
    const password = formData.get("password");

    console.log('🔐 Forsøger login for:', email);

    const result = await api.login(email, password);
    
    if (result && (result.token || result.id || result.email)) {
        // Gem bruger data i localStorage
        if (result.token) localStorage.setItem("authToken", result.token);
        localStorage.setItem("userId", result.id);
        localStorage.setItem("userEmail", result.email || email);
        localStorage.setItem("userName", result.name || result.firstname || result.email);
        localStorage.setItem("userType", result.userType || result.role || "CUSTOMER");

        updateNavigation();

        alert(`Login succesfuldt! Velkommen tilbage, ${result.name || result.firstname || email}! 🎉`);

        const userType = result.userType || result.role || "CUSTOMER";
        if (userType === "ADMIN") {
            if (typeof renderAdminSection === "function") renderAdminSection();
        } else {
            renderSection("shop");
        }
    } else {
        alert("Login fejlede. Tjek venligst din email og adgangskode.");
    }
}

// Signup handler - OPDATERET MED VARIABLER OG ADRESSE
async function handleSignup(event) {
    event.preventDefault();
    const formData = new FormData(event.target);

    // 1. DU SKAL DEFINERE DISSE VARIABLER FØRST (Det er her fejlen ligger)
    const password = formData.get("password");
    const confirmPassword = formData.get("confirmPassword");

    // 2. Tjek om de matcher
    if (password !== confirmPassword) {
        alert("Adgangskoderne matcher ikke!");
        return;
    }

    // 3. Nu kan du bygge dit objekt, fordi 'password' nu er defineret som en variabel
    const userData = {
        firstname: formData.get("firstname"),
        lastname: formData.get("lastname"),
        email: formData.get("email"),
        password: password, // Nu peger denne på variablen fra linje 6
        address: formData.get("address") || "",
        city: formData.get("city") || "",
        postalCode: formData.get("postalCode") || "", 
        phone: formData.get("phone") || ""
    };

    console.log("📤 Sender signup data:", userData);

    const result = await api.signup(userData);

    if (result && result.id) {
        alert(`Konto oprettet! Velkommen, ${userData.firstname}! 🎉`);
        renderSection("login");
    } else {
        alert("Registrering fejlede: " + (result?.message || "Email er måske allerede i brug."));
    }
}

// Navigation click handlers
document.addEventListener("DOMContentLoaded", async () => {
  // Hent produkter fra backend først
  await loadProducts();

  // Opdater navigation baseret på login status
  updateNavigation();
  
  // Opdater cart counter
  updateCartCounter();

  // Vis kurv når kurv icon bliver trykket på 
  const cartBtn = document.querySelector('.cart-btn');
  if (cartBtn) {
      cartBtn.addEventListener('click', (e) => {
          e.preventDefault();
          renderSection('cart');
      });
  }

  // Tjek om brugeren er admin og vis admin panel
  const userType = localStorage.getItem("userType");
  const userName = localStorage.getItem("userName");

  // SIKKERHEDSCHECK: Kun vis admin panel hvis både userType ER ADMIN og brugeren er logget ind
  if (userType === "ADMIN" && userName) {
    // Vis admin panel for admin brugere
    if (typeof renderAdminSection === "function") {
      renderAdminSection();
    } else {
      console.warn("renderAdminSection not available, showing shop instead");
      renderSection("shop");
    }
  } else {
    // Vis shop for almindelige brugere eller ikke-indloggede
    // Hvis nogen har manipuleret localStorage til at sige ADMIN uden at være logget ind, ryd det
    if (userType === "ADMIN" && !userName) {
      console.warn("Invalid admin state detected, clearing localStorage");
      localStorage.removeItem("userType");
    }
    renderSection("shop");
  }

  // Add click handlers to nav links
  document.querySelectorAll(".nav-menu a").forEach((link) => {
    link.addEventListener("click", (e) => {
      e.preventDefault();
      const section = e.target.dataset.section;
      if (section) {
        renderSection(section);
      }
    });
  });
});

// Function to fetch and render GIFTS
async function loadGiftProducts() {
  const container = document.getElementById("gift-container");

  if (!container) return; // Safety check

  try {
    const response = await fetch(`${API_BASE_URL}/gift-products`);
    const gifts = await response.json();

    // Clear "Loading..." text
    container.innerHTML = "";

    gifts.forEach((gift) => {
      // 1. Check Stock: Is it <= 0?
      const isSoldOut = gift.stockQuantity <= 0;

      // 2. CSS Classes:
      const cardClass = isSoldOut ? "product-card sold-out" : "product-card";

      // 3. Button Logic: Disable clicking if sold out
      const btnState = isSoldOut
        ? 'disabled style="background-color:#ccc; cursor:not-allowed;"'
        : "";
      const btnText = isSoldOut ? "Ikke på lager" : "Vælg Gave";

      // 4. Badge Logic: The red sticker
      const badge = isSoldOut
        ? '<span class="badge-sold-out">UDSOLGT</span>'
        : "";

      // 5. Image Logic: Use your existing helper or fallback
      const imgHtml = getProductImage(gift);

      // Build HTML
      const html = `
                <div class="${cardClass}" style="position:relative;">
                    ${badge}
                    <div class="product-image" style="height:200px;">
                        ${imgHtml}
                    </div>
                    <div class="product-info">
                        <h3>${gift.name}</h3>
                        <p>${gift.description || ""}</p>
                        <div class="product-price">
                            <span class="price">Lager: ${
                              gift.stockQuantity
                            }</span>
                            <button class="add-to-cart" onclick="selectGift(${
                              gift.id
                            })" ${btnState}>
                                ${btnText}
                            </button>
                        </div>
                    </div>
                </div>
            `;
      container.innerHTML += html;
    });
  } catch (error) {
    console.error("Error loading gifts:", error);
    container.innerHTML = "<p>Kunne ikke hente gaveprodukter.</p>";
  }
}

// Opdater navigation baseret på login status
function updateNavigation() {
  const authMenu = document.getElementById("auth-menu");
  const userName = localStorage.getItem("userName");
  const userType = localStorage.getItem("userType");

  if (userName) {
    // Bruger er logget ind
    authMenu.innerHTML = `
            <li style="color: #2d5f2e; font-weight: 500; padding: 0 1rem;">
                ${userName} ${userType === "ADMIN" ? "(Admin)" : ""}
            </li>
            <li><a href="#" onclick="handleLogout(); return false;" style="color: #d32f2f;">Log ud</a></li>
        `;

    // Opdater navigation menu for admin
    if (userType === "ADMIN") {
      const navMenu = document.querySelector(".nav-menu");
      if (navMenu) {
        navMenu.innerHTML = `
                    <li><a href="#" onclick="if(typeof renderAdminSection === 'function') renderAdminSection(); return false;">Admin Panel</a></li>
                `;
      }

      // Opdater logo text
      const logoText = document.querySelector(".logo-text");
      if (logoText) {
        logoText.textContent = "BilligT-Shirt Admin";
      }
    }
  } else {
    // Bruger er ikke logget ind
    authMenu.innerHTML = `
            <li><a href="#" data-section="login">Log ind</a></li>
            <li><a href="#" data-section="signup">Tilmeld</a></li>
        `;

    // Genaktiver click handlers for auth links
    authMenu.querySelectorAll("a").forEach((link) => {
      link.addEventListener("click", (e) => {
        e.preventDefault();
        const section = e.target.dataset.section;
        if (section) {
          renderSection(section);
        }
      });
    });
  }
}

// Logout handler
function handleLogout() {
  // Fjern alle bruger data fra localStorage
  localStorage.removeItem("authToken");
  localStorage.removeItem("userId");
  localStorage.removeItem("userEmail");
  localStorage.removeItem("userName");
  localStorage.removeItem("userType");

  // Nulstil navigation til standard shop navigation
  const navMenu = document.querySelector(".nav-menu");
  if (navMenu) {
    navMenu.innerHTML = `
            <li><a href="#" data-section="shop">Butik</a></li>
            <li><a href="#" data-section="collections">Kollektioner</a></li>
            <li><a href="#" data-section="about">Om Os</a></li>
        `;

    // Genaktiver click handlers
    document.querySelectorAll(".nav-menu a").forEach((link) => {
      link.addEventListener("click", (e) => {
        e.preventDefault();
        const section = e.target.dataset.section;
        if (section) {
          renderSection(section);
        }
      });
    });
  }

  // Nulstil logo text
  const logoText = document.querySelector(".logo-text");
  if (logoText) {
    logoText.textContent = "BilligT-Shirt";
  }

  // Opdater auth navigation
  updateNavigation();

  // Redirect til shop (tvungen)
  renderSection("shop");

  alert("Du er nu logget ud. På gensyn! 👋");
}

// Helper for testing without backend
function renderOfflineCart(content, footer) {
    const demoCart = {
        items: [
            { productName: "Offline T-Shirt", quantity: 2, pricePerUnit: 150, rowTotal: 300 },
        ],
        grandTotal: 300
    };
    renderCartItems(demoCart, content, footer);
}

// --- TASK 2.3 & 3.2: GAVE STATUS LOGIK ---
function updateGiftStatus(cart) {
    const statusDiv = document.getElementById('gift-status-message');
    if (!statusDiv) return;

    // Ryd gamle klasser
    statusDiv.className = 'gift-status-box';

    // TASK 3.3: Check if gift has been selected
    if (cart.freeGift) {
        statusDiv.textContent = "✅ Du har valgt din gratis gave!";
        statusDiv.classList.add('gift-success');
    }
    // Tjek om man kan vælge gave (Boolean fra Backend)
    else if (cart.canSelectFreeGift === true) {
        // TASK 3.2: Vis Succes med knap til at åbne modal
        statusDiv.innerHTML = `
            🎉 Tillykke! Du har optjent en gratis gave!
            <button onclick="showGiftSelectionModal()" 
                    style="margin-left: 10px; padding: 8px 16px; background: #2d5f2e; color: white; border: none; border-radius: 5px; cursor: pointer; font-weight: bold;">
                Vælg gave nu
            </button>
        `;
        statusDiv.classList.add('gift-success');
    } else {
        // TASK 2.3: Vis hvor meget der mangler
        // Vi bruger 'missingForFreeGift' fra backenden. 
        // Hvis den er null/undefined sætter vi den til 0 for en sikkerheds skyld.
        const missing = cart.missingForFreeGift || 0;
        
        statusDiv.textContent = `⚠️ Du mangler ${missing} kr. for at få en gratis gave.`;
        statusDiv.classList.add('gift-warning');
    }
}

// ==========================================
// TASK 2.2: GIFT SELECTION MODAL
// ==========================================

// Show gift selection modal popup
async function showGiftSelectionModal() {
    // Fetch available gifts
    const gifts = await api.getGiftProducts();
    
    if (!gifts || gifts.length === 0) {
        console.log('Ingen gaver tilgængelige');
        return;
    }

    // Create modal HTML
    const modalHtml = `
        <div id="gift-modal" class="gift-modal">
            <div class="gift-modal-content">
                <div class="gift-modal-header">
                    <h2>🎁 Vælg Din Gratis Gave!</h2>
                    <p>Tillykke! Du har optjent en gratis gave. Vælg én gave nedenfor:</p>
                    <button class="gift-modal-close" onclick="closeGiftModal()">&times;</button>
                </div>
                <div class="gift-modal-body">
                    <div class="gift-options-grid">
                        ${gifts.map(gift => {
                            const isAvailable = gift.stockQuantity > 0;
                            const disabledAttr = isAvailable ? '' : 'disabled';
                            const disabledClass = isAvailable ? '' : 'disabled';
                            
                            return `
                                <div class="gift-option ${disabledClass}">
                                    <div class="gift-option-image">
                                        ${getProductImage(gift)}
                                    </div>
                                    <div class="gift-option-info">
                                        <h3>${gift.name}</h3>
                                        <p>${gift.description || ''}</p>
                                        <p class="gift-stock">Lager: ${gift.stockQuantity} stk.</p>
                                        ${isAvailable 
                                            ? `<button class="btn-select-gift" onclick="selectGiftFromModal(${gift.id}, '${gift.name}')">Vælg denne gave</button>`
                                            : `<button class="btn-select-gift" disabled>Ikke på lager</button>`
                                        }
                                    </div>
                                </div>
                            `;
                        }).join('')}
                    </div>
                </div>
            </div>
        </div>
    `;

    // Add modal to body
    const modalContainer = document.createElement('div');
    modalContainer.innerHTML = modalHtml;
    document.body.appendChild(modalContainer.firstElementChild);

    // Show modal with animation
    setTimeout(() => {
        document.getElementById('gift-modal').classList.add('show');
    }, 10);
}

// Close gift selection modal
function closeGiftModal() {
    const modal = document.getElementById('gift-modal');
    if (modal) {
        modal.classList.remove('show');
        setTimeout(() => {
            modal.remove();
        }, 300);
    }
}

// TASK 2.3: Handle gift selection from modal
async function selectGiftFromModal(giftId, giftName) {
    try {
        // Show loading state
        const modal = document.getElementById('gift-modal');
        if (modal) {
            modal.style.opacity = '0.6';
            modal.style.pointerEvents = 'none';
        }

        // Call API to select gift
        await api.selectFreeGift(giftId);

        // Close modal
        closeGiftModal();

        // Reload cart to show updated data with gift
        await loadCart();

    } catch (error) {
        console.error('Fejl ved valg af gave:', error.message);
        // Reset modal state
        const modal = document.getElementById('gift-modal');
        if (modal) {
            modal.style.opacity = '1';
            modal.style.pointerEvents = 'auto';
        }
    }
}

