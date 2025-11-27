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
        return '👕';
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
                    <div class="hero-image-placeholder">👕</div>
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
                        <div class="feature-icon">🌱</div>
                        <h3>100% Økologisk</h3>
                        <p>Alle vores t-shirts er lavet af certificeret økologisk bomuld</p>
                    </div>
                    <div class="feature">
                        <div class="feature-icon">♻️</div>
                        <h3>Bæredygtig</h3>
                        <p>Miljøvenlig produktion og fair handel</p>
                    </div>
                    <div class="feature">
                        <div class="feature-icon">⭐</div>
                        <h3>Premium Kvalitet</h3>
                        <p>Holdbare materialer der holder til daglig brug</p>
                    </div>
                    <div class="feature">
                        <div class="feature-icon">💚</div>
                        <h3>Fair Priser</h3>
                        <p>Høj kvalitet til en pris alle har råd til</p>
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

// Navigation click handlers
document.addEventListener('DOMContentLoaded', async () => {
    // Hent produkter fra backend først
    await loadProducts();
    
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
