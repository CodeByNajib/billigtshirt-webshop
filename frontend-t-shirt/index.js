// Product data - vil blive hentet fra backend
let products = [];

// Hent produkter fra backend
async function loadProducts() {
    products = await api.getProducts();
    
    // Fallback data hvis backend ikke er tilgængelig
    if (!products || products.length === 0) {
        console.warn('Bruger fallback produkter - backend er ikke tilgængelig');
        products = [
            { id: 1, name: 'Klassisk Grøn T-Shirt', description: '100% økologisk bomuld', price: 149, imageUrl: 'https://images.unsplash.com/photo-1521572163474-6864f9cf17ab?w=400&h=400&fit=crop' },
            { id: 2, name: 'Premium Hvid T-Shirt', description: 'Blød og åndbar', price: 169, imageUrl: 'https://images.unsplash.com/photo-1622445275463-afa2ab738c34?w=400&h=400&fit=crop' },
            { id: 3, name: 'Skov Edition', description: 'Bæredygtig produktion', price: 189, imageUrl: 'https://images.unsplash.com/photo-1583743814966-8936f5b7be1a?w=400&h=400&fit=crop' },
            { id: 4, name: 'Minimalistisk Design', description: 'Tidløs stil', price: 159, imageUrl: 'https://images.unsplash.com/photo-1618354691373-d851c5c3a990?w=400&h=400&fit=crop' },
            { id: 5, name: 'Komfort Pasform', description: 'Perfekt pasform', price: 179, imageUrl: 'https://images.unsplash.com/photo-1576566588028-4147f3842f27?w=400&h=400&fit=crop' },
            { id: 6, name: 'Natur Kollektion', description: 'Miljøvenlig farve', price: 199, imageUrl: 'https://images.unsplash.com/photo-1562157873-818bc0726f68?w=400&h=400&fit=crop' }
        ];
    }
    
    // Render siden efter produkter er hentet
    const currentSection = getCurrentSection();
    renderSection(currentSection);
}

// Hjælpefunktion til at formatere pris
function formatPrice(price) {
    return typeof price === 'number' ? `${price} kr` : price;
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
            <path d="M50 15L40 30H60L50 15Z" fill="#2d5f2e" opacity="0.3"/>
            <rect x="35" y="30" width="30" height="40" rx="2" fill="#2d5f2e" opacity="0.5"/>
            <rect x="25" y="35" width="50" height="25" rx="2" fill="#2d5f2e" opacity="0.4"/>
            <path d="M25 60L35 85H65L75 60" stroke="#2d5f2e" stroke-width="2" fill="none"/>
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
                            <path d="M100 30L80 60H120L100 30Z" fill="#2d5f2e" opacity="0.3"/>
                            <rect x="70" y="60" width="60" height="80" rx="5" fill="#2d5f2e" opacity="0.5"/>
                            <rect x="50" y="70" width="100" height="50" rx="5" fill="#2d5f2e" opacity="0.4"/>
                            <path d="M50 120L70 170H130L150 120" stroke="#2d5f2e" stroke-width="3" fill="none"/>
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
                ${products.map(product => `
                    <div class="product-card">
                        <div class="product-image">${getProductImage(product)}</div>
                        <div class="product-info">
                            <h3>${product.name}</h3>
                            <p>${product.description}</p>
                            <div class="product-price">
                                <span class="price">${formatPrice(product.price)}</span>
                                <button class="add-to-cart" onclick="addToCart(${product.id})">Tilføj</button>
                            </div>
                        </div>
                    </div>
                `).join('')}
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
                ${products.map(product => `
                    <div class="product-card">
                        <div class="product-image">${getProductImage(product)}</div>
                        <div class="product-info">
                            <h3>${product.name}</h3>
                            <p>${product.description}</p>
                            <div class="product-price">
                                <span class="price">${formatPrice(product.price)}</span>
                                <button class="add-to-cart" onclick="addToCart(${product.id})">Tilføj til kurv</button>
                            </div>
                        </div>
                    </div>
                `).join('')}
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
                                <path d="M30 10L25 20L30 50L35 20L30 10Z" fill="#2d5f2e" opacity="0.3"/>
                                <circle cx="20" cy="25" r="3" fill="#2d5f2e"/>
                                <circle cx="40" cy="25" r="3" fill="#2d5f2e"/>
                                <circle cx="25" cy="35" r="2" fill="#2d5f2e"/>
                                <circle cx="35" cy="35" r="2" fill="#2d5f2e"/>
                                <path d="M28 45C28 45 30 48 32 45" stroke="#2d5f2e" stroke-width="2"/>
                            </svg>
                        </div>
                        <h3>100% Økologisk</h3>
                        <p>Alle vores t-shirts er lavet af certificeret økologisk bomuld</p>
                    </div>
                    <div class="feature">
                        <div class="feature-icon">
                            <svg width="60" height="60" viewBox="0 0 60 60" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <path d="M30 10L20 25L30 30L40 25L30 10Z" stroke="#2d5f2e" stroke-width="2" fill="none"/>
                                <path d="M20 25L20 35L30 40L40 35L40 25" stroke="#2d5f2e" stroke-width="2" fill="none"/>
                                <path d="M30 30L30 40" stroke="#2d5f2e" stroke-width="2"/>
                                <circle cx="30" cy="45" r="5" stroke="#2d5f2e" stroke-width="2" fill="none"/>
                            </svg>
                        </div>
                        <h3>Bæredygtig</h3>
                        <p>Miljøvenlig produktion og fair handel</p>
                    </div>
                    <div class="feature">
                        <div class="feature-icon">
                            <svg width="60" height="60" viewBox="0 0 60 60" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <polygon points="30,10 35,25 50,25 38,35 42,50 30,40 18,50 22,35 10,25 25,25" fill="#2d5f2e" opacity="0.5"/>
                                <polygon points="30,10 35,25 50,25 38,35 42,50 30,40 18,50 22,35 10,25 25,25" stroke="#2d5f2e" stroke-width="2" fill="none"/>
                            </svg>
                        </div>
                        <h3>Premium Kvalitet</h3>
                        <p>Holdbare materialer der holder til daglig brug</p>
                    </div>
                    <div class="feature">
                        <div class="feature-icon">
                            <svg width="60" height="60" viewBox="0 0 60 60" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <path d="M30 15C30 15 20 20 20 30C20 40 30 45 30 45C30 45 40 40 40 30C40 20 30 15 30 15Z" stroke="#2d5f2e" stroke-width="2" fill="#2d5f2e" opacity="0.3"/>
                                <path d="M25 28L28 32L35 23" stroke="#2d5f2e" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
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
                                <circle cx="30" cy="30" r="28" stroke="#2d5f2e" stroke-width="3"/>
                                <circle cx="30" cy="22" r="8" fill="#2d5f2e"/>
                                <path d="M15 48C15 40 21 35 30 35C39 35 45 40 45 48" stroke="#2d5f2e" stroke-width="3" stroke-linecap="round"/>
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
                                <path d="M20 50V48C20 43 22 40 30 40C38 40 40 43 40 48V50" stroke="#2d5f2e" stroke-width="3" stroke-linecap="round"/>
                                <path d="M30 10L25 20H35L30 10Z" fill="#2d5f2e"/>
                                <rect x="25" y="20" width="10" height="15" rx="1" fill="#2d5f2e"/>
                                <rect x="20" y="24" width="20" height="8" rx="1" fill="#2d5f2e"/>
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
    `
};

// Render function
function renderSection(sectionName) {
    const content = document.getElementById('app-content');
    if (sections[sectionName]) {
        content.innerHTML = sections[sectionName]();
    }
    
    // Update active nav link
    document.querySelectorAll('.nav-menu a').forEach(link => {
        link.style.color = link.dataset.section === sectionName ? '#4a8c4a' : '#2d4a2d';
    });
}

// Get current section from URL or default
function getCurrentSection() {
    const path = window.location.pathname;
    if (path.includes('collections')) return 'collections';
    if (path.includes('about')) return 'about';
    if (path.includes('login')) return 'login';
    if (path.includes('signup')) return 'signup';
    return 'shop';
}

// Add to cart function - nu med backend integration
async function addToCart(productId) {
    const product = products.find(p => p.id === productId);
    
    // Prøv at tilføje til backend kurv
    const result = await api.addToCart(productId, 1);
    
    if (result) {
        alert(`${product.name} tilføjet til kurven! 🛒`);
    } else {
        // Fallback hvis backend ikke virker
        alert(`${product.name} tilføjet til kurven! 🛒 (Offline mode)`);
    }
}

// Login handler
async function handleLogin(event) {
    event.preventDefault();
    const formData = new FormData(event.target);
    const email = formData.get('email');
    const password = formData.get('password');
    
    const result = await api.login(email, password);
    
    if (result && result.token) {
        // Gem bruger data i localStorage
        localStorage.setItem('authToken', result.token);
        localStorage.setItem('userId', result.id);
        localStorage.setItem('userEmail', result.email);
        localStorage.setItem('userName', result.name);
        localStorage.setItem('userType', result.userType);
        
        // Opdater navigation
        updateNavigation();
        
        alert(`${result.message || 'Login succesfuldt!'}\nVelkommen tilbage, ${result.name}! 🎉`);
        
        // Redirect baseret på userType
        if (result.userType === 'ADMIN') {
            // Redirect til admin panel hvis det er en admin
            window.location.href = 'admin.html';
        } else {
            // Redirect til shop for almindelige kunder
            renderSection('shop');
        }
    } else {
        alert('Login fejlede. Tjek venligst din email og adgangskode.');
    }
}

// Signup handler
async function handleSignup(event) {
    event.preventDefault();
    const formData = new FormData(event.target);
    
    const password = formData.get('password');
    const confirmPassword = formData.get('confirmPassword');
    
    // Tjek om adgangskoder matcher
    if (password !== confirmPassword) {
        alert('Adgangskoderne matcher ikke!');
        return;
    }
    
    const userData = {
        firstname: formData.get('firstname'),
        lastname: formData.get('lastname'),
        email: formData.get('email'),
        password: password
    };
    
    const result = await api.signup(userData);
    
    if (result && result.id) {
        alert(`Konto oprettet succesfuldt!\nVelkommen til BilligT-Shirt, ${userData.firstname}! 🎉\n\nDu kan nu logge ind med din email.`);
        // Redirect til login efter signup
        renderSection('login');
    } else {
        alert('Registrering fejlede. Email er måske allerede i brug. Prøv venligst igen.');
    }
}

// Navigation click handlers
document.addEventListener('DOMContentLoaded', async () => {
    // Hent produkter fra backend først
    await loadProducts();
    
    // Opdater navigation baseret på login status
    updateNavigation();
    
    // Add click handlers to nav links
    document.querySelectorAll('.nav-menu a').forEach(link => {
        link.addEventListener('click', (e) => {
            e.preventDefault();
            const section = e.target.dataset.section;
            if (section) {
                renderSection(section);
            }
        });
    });
});

// Opdater navigation baseret på login status
function updateNavigation() {
    const authMenu = document.getElementById('auth-menu');
    const userName = localStorage.getItem('userName');
    const userType = localStorage.getItem('userType');
    
    if (userName) {
        // Bruger er logget ind
        authMenu.innerHTML = `
            <li style="color: #2d5f2e; font-weight: 500; padding: 0 1rem;">
                ${userName} ${userType === 'ADMIN' ? '(Admin)' : ''}
            </li>
            <li><a href="#" onclick="handleLogout(); return false;" style="color: #d32f2f;">Log ud</a></li>
        `;
    } else {
        // Bruger er ikke logget ind
        authMenu.innerHTML = `
            <li><a href="#" data-section="login">Log ind</a></li>
            <li><a href="#" data-section="signup">Tilmeld</a></li>
        `;
        
        // Genaktiver click handlers for auth links
        authMenu.querySelectorAll('a').forEach(link => {
            link.addEventListener('click', (e) => {
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
    localStorage.removeItem('authToken');
    localStorage.removeItem('userId');
    localStorage.removeItem('userEmail');
    localStorage.removeItem('userName');
    localStorage.removeItem('userType');
    
    // Opdater navigation
    updateNavigation();
    
    // Redirect til shop
    renderSection('shop');
    
    alert('Du er nu logget ud. På gensyn! 👋');
}
