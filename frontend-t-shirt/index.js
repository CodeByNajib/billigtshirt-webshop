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

  // Render siden efter produkter er hentet
  const currentSection = getCurrentSection();
  renderSection(currentSection);
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
                                <button class="add-to-cart" onclick="addToCart(${
                                  product.id
                                })">Tilføj</button>
                            </div>
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

                <button class="btn-primary" onclick="alert('Gå til kassen logic her')">Gå til kassen →</button>
            </div>
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
                                <button class="add-to-cart" onclick="addToCart(${
                                  product.id
                                })">Tilføj til kurv</button>
                            </div>
                        </div>
                    </div>
                `
                  )
                  .join("")}
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
  if (sections[sectionName]) {
    content.innerHTML = sections[sectionName]();
  }

  // If the user navigated to 'gifts', we need to fetch the data
  if (sectionName === "gifts") {
    loadGiftProducts();
  }

  if (sectionName === "cart") {
    loadCart(); // Triggers the function we just added
  }

  // Update active nav link
  document.querySelectorAll(".nav-menu a").forEach((link) => {
    link.style.color =
      link.dataset.section === sectionName ? "#4a8c4a" : "#2d4a2d";
  });
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

// Add to cart function - nu med backend integration
async function addToCart(productId) {
  const product = products.find((p) => p.id === productId);

  // Prøv at tilføje til backend kurv
  const result = await api.addToCart(productId, 1);

  if (result) {
    alert(`${product.name} tilføjet til kurven! 🛒`);
  } else {
    // Fallback hvis backend ikke virker
    alert(`${product.name} tilføjet til kurven! 🛒 (Offline mode)`);
  }
}

// ==========================================
// CART FUNCTIONALITY (Tasks 1.3 & 1.4)
// ==========================================

// TASK 1.3: Fetch Cart Data from Backend
async function loadCart() {
    const content = document.getElementById('cart-content');
    const footer = document.getElementById('cart-footer');
    
    // Safety check: if we aren't on the cart page, stop
    if (!content) return;

    try {
        // Fetch from your Spring Boot Endpoint
        // Note: Ensure your Backend is running on port 8080
        const response = await fetch('http://localhost:8080/api/cart'); 
        
        if (!response.ok) throw new Error('Kunne ikke hente kurv');
        
        const cartData = await response.json();
        
        // TASK 1.4: Render the data
        renderCartItems(cartData, content, footer);
        
    } catch (error) {
        console.error("Cart error:", error);
        content.innerHTML = `<p style="text-align:center; color:red;">Der skete en fejl: ${error.message}</p>`;
        
        // Optional: Show offline fallback for testing
        renderOfflineCart(content, footer);
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
                </tr>
            </thead>
            <tbody>
    `;

    // 3. Loop through the "items" list from your Java DTO
    cart.items.forEach(item => {
        html += `
            <tr style="border-bottom: 1px solid #eee;">
                <td style="padding: 15px 10px;">
                    <strong>${item.productName}</strong>
                </td>
                <td style="padding: 15px 10px;">
                    ${item.quantity} stk.
                </td>
                <td style="padding: 15px 10px;">
                    ${item.pricePerUnit} kr.
                </td>
                <td style="padding: 15px 10px; font-weight: bold;">
                    ${item.rowTotal} kr.
                </td>
            </tr>
        `;
    });

    html += '</tbody></table>';
    contentElement.innerHTML = html;

    // 4. Update Footer (Grand Total)
    document.getElementById('cart-total-price').textContent = cart.grandTotal;
    footerElement.style.display = 'block';

    // --- NYT: Opdater statusboksen baseret på tallene ---
    updateGiftStatus(cart);
}


// Login handler
async function handleLogin(event) {
  event.preventDefault();
  const formData = new FormData(event.target);
  const email = formData.get("email");
  const password = formData.get("password");

  const result = await api.login(email, password);

  if (result && result.token) {
    // Gem bruger data i localStorage
    localStorage.setItem("authToken", result.token);
    localStorage.setItem("userId", result.id);
    localStorage.setItem("userEmail", result.email);
    localStorage.setItem("userName", result.name);
    localStorage.setItem("userType", result.userType);

    // Opdater navigation
    updateNavigation();

    alert(
      `${result.message || "Login succesfuldt!"}\nVelkommen tilbage, ${
        result.name
      }! 🎉`
    );

    // Redirect baseret på userType
    if (result.userType === "ADMIN") {
      // Vis admin panel hvis det er en admin
      if (typeof renderAdminSection === "function") {
        renderAdminSection();
      }
    } else {
      // Vis shop for almindelige kunder
      renderSection("shop");
    }
  } else {
    alert("Login fejlede. Tjek venligst din email og adgangskode.");
  }
}

// Signup handler
async function handleSignup(event) {
  event.preventDefault();
  const formData = new FormData(event.target);

  const password = formData.get("password");
  const confirmPassword = formData.get("confirmPassword");

  // Tjek om adgangskoder matcher
  if (password !== confirmPassword) {
    alert("Adgangskoderne matcher ikke!");
    return;
  }

  const userData = {
    firstname: formData.get("firstname"),
    lastname: formData.get("lastname"),
    email: formData.get("email"),
    password: password,
  };

  const result = await api.signup(userData);

  if (result && result.id) {
    alert(
      `Konto oprettet succesfuldt!\nVelkommen til BilligT-Shirt, ${userData.firstname}! 🎉\n\nDu kan nu logge ind med din email.`
    );
    // Redirect til login efter signup
    renderSection("login");
  } else {
    alert(
      "Registrering fejlede. Email er måske allerede i brug. Prøv venligst igen."
    );
  }
}

// Navigation click handlers
document.addEventListener("DOMContentLoaded", async () => {
  // Hent produkter fra backend først
  await loadProducts();

  // Opdater navigation baseret på login status
  updateNavigation();

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
  const GIFT_API_URL = "http://localhost:8080/api/gift-products"; // Endpoint
  const container = document.getElementById("gift-container");

  if (!container) return; // Safety check

  try {
    const response = await fetch(GIFT_API_URL);
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

    // Tjek om man kan vælge gave (Boolean fra Backend)
    if (cart.canSelectFreeGift === true) {
        // TASK 3.2: Vis Succes
        statusDiv.textContent = "🎉 Tillykke! Du har optjent en gratis gave. Gå til 'Gaver' for at vælge.";
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
