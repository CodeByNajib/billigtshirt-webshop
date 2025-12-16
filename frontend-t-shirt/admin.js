// admin.js

const API_URL = "http://localhost:8080/api/admin/gift-config";
const PRODUCTS_API_URL = "http://localhost:8080/api/products";
const GIFT_PRODUCTS_API_URL = "http://localhost:8080/api/gift-products";
const CUSTOMERS_API_URL = "http://localhost:8080/api/customers";

// Admin sections objekt
const adminSections = {
    dashboard: () => `
        <main class="admin-container">
            <div style="display: flex; align-items: center; gap: 1rem; margin-bottom: 1rem;">
                <svg width="50" height="50" viewBox="0 0 50 50" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <!-- Settings/Admin gear icon -->
                    <circle cx="25" cy="25" r="20" fill="#e8f5e9"/>
                    <circle cx="25" cy="25" r="8" fill="#2d5f2e"/>
                    <circle cx="25" cy="25" r="8" stroke="#4a8c4a" stroke-width="2" fill="none"/>
                    <!-- Gear teeth -->
                    <rect x="23" y="5" width="4" height="8" rx="1" fill="#2d5f2e"/>
                    <rect x="23" y="37" width="4" height="8" rx="1" fill="#2d5f2e"/>
                    <rect x="5" y="23" width="8" height="4" rx="1" fill="#2d5f2e"/>
                    <rect x="37" y="23" width="8" height="4" rx="1" fill="#2d5f2e"/>
                    <rect x="11" y="11" width="4" height="8" rx="1" fill="#4a8c4a" transform="rotate(45 13 15)"/>
                    <rect x="35" y="11" width="4" height="8" rx="1" fill="#4a8c4a" transform="rotate(-45 37 15)"/>
                    <rect x="11" y="31" width="4" height="8" rx="1" fill="#4a8c4a" transform="rotate(-45 13 35)"/>
                    <rect x="35" y="31" width="4" height="8" rx="1" fill="#4a8c4a" transform="rotate(45 37 35)"/>
                </svg>
                <h1 style="margin: 0;">Admin Panel</h1>
            </div>
            <p>Velkommen til admin panelet. Vælg en funktion nedenfor:</p>
            
            <div class="admin-grid">
                <div class="admin-card" onclick="renderAdminSection('products')">
                    <div class="admin-card-icon">
                        <svg width="80" height="80" viewBox="0 0 80 80" fill="none" xmlns="http://www.w3.org/2000/svg">
                            <!-- Box/Package icon -->
                            <rect x="20" y="25" width="40" height="40" rx="2" fill="#e8f5e9" stroke="#2d5f2e" stroke-width="2"/>
                            <path d="M20 35 L40 45 L60 35" stroke="#2d5f2e" stroke-width="2" fill="none"/>
                            <path d="M40 45 L40 65" stroke="#2d5f2e" stroke-width="2"/>
                            <path d="M20 25 L40 15 L60 25" stroke="#4a8c4a" stroke-width="2" fill="none"/>
                            <circle cx="50" cy="30" r="3" fill="#4a8c4a"/>
                        </svg>
                    </div>
                    <h3>Produkter</h3>
                    <p>Administrer produkter (CRUD)</p>
                </div>
                
                <div class="admin-card" onclick="renderAdminSection('gifts')">
                    <div class="admin-card-icon">
                        <svg width="80" height="80" viewBox="0 0 80 80" fill="none" xmlns="http://www.w3.org/2000/svg">
                            <!-- Gift box icon -->
                            <rect x="25" y="35" width="30" height="30" rx="2" fill="#e8f5e9" stroke="#2d5f2e" stroke-width="2"/>
                            <rect x="23" y="30" width="34" height="8" rx="1" fill="#4a8c4a"/>
                            <path d="M40 30 L40 65" stroke="#2d5f2e" stroke-width="2"/>
                            <path d="M25 35 L55 35" stroke="#2d5f2e" stroke-width="2"/>
                            <!-- Ribbon -->
                            <path d="M40 30 Q35 20 30 25 Q32 28 40 30" fill="#dc3545"/>
                            <path d="M40 30 Q45 20 50 25 Q48 28 40 30" fill="#dc3545"/>
                            <circle cx="40" cy="30" r="2" fill="#dc3545"/>
                        </svg>
                    </div>
                    <h3>Gave Produkter</h3>
                    <p>Administrer gaver og grænse</p>
                </div>
                
                <div class="admin-card" onclick="renderAdminSection('customers')">
                    <div class="admin-card-icon">
                        <svg width="80" height="80" viewBox="0 0 80 80" fill="none" xmlns="http://www.w3.org/2000/svg">
                            <!-- Multiple users icon -->
                            <circle cx="30" cy="30" r="8" fill="#4a8c4a"/>
                            <path d="M18 52C18 45 23 41 30 41C37 41 42 45 42 52" stroke="#2d5f2e" stroke-width="2.5" stroke-linecap="round"/>
                            <circle cx="50" cy="32" r="7" fill="#e8f5e9" stroke="#2d5f2e" stroke-width="2"/>
                            <path d="M40 54C40 48 44 44 50 44C56 44 60 48 60 54" stroke="#2d5f2e" stroke-width="2" stroke-linecap="round"/>
                        </svg>
                    </div>
                    <h3>Kunder</h3>
                    <p>Administrer kunder (CRUD)</p>
                </div>
            </div>
        </main>
    `,

    products: () => `
        <main class="admin-container">
            <div class="admin-header">
                <div style="display: flex; align-items: center; gap: 0.5rem;">
                    <svg width="40" height="40" viewBox="0 0 40 40" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <rect x="10" y="12" width="20" height="20" rx="1" fill="#e8f5e9" stroke="#2d5f2e" stroke-width="1.5"/>
                        <path d="M10 17 L20 22 L30 17" stroke="#2d5f2e" stroke-width="1.5" fill="none"/>
                        <path d="M20 22 L20 32" stroke="#2d5f2e" stroke-width="1.5"/>
                        <path d="M10 12 L20 7 L30 12" stroke="#4a8c4a" stroke-width="1.5" fill="none"/>
                    </svg>
                    <h1 style="margin: 0;">Produkter Administration</h1>
                </div>
                <button class="btn-secondary" onclick="renderAdminSection('dashboard')">← Tilbage til Dashboard</button>
            </div>

            <!-- Navigation Tabs -->
            <div style="display: flex; gap: 0.5rem; margin-bottom: 2rem; border-bottom: 2px solid #e0e0e0; padding-bottom: 0;">
                <button class="product-tab active" onclick="showProductTab('create')" id="tab-product-create">
                    <svg width="18" height="18" viewBox="0 0 16 16" fill="currentColor" style="vertical-align: middle; margin-right: 4px;">
                        <path d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z"/>
                    </svg>
                    Opret Produkt
                </button>
                <button class="product-tab" onclick="showProductTab('inventory')" id="tab-product-inventory">
                    <svg width="18" height="18" viewBox="0 0 16 16" fill="currentColor" style="vertical-align: middle; margin-right: 4px;">
                        <path d="M1 2.5A1.5 1.5 0 0 1 2.5 1h3A1.5 1.5 0 0 1 7 2.5v3A1.5 1.5 0 0 1 5.5 7h-3A1.5 1.5 0 0 1 1 5.5v-3zm8 0A1.5 1.5 0 0 1 10.5 1h3A1.5 1.5 0 0 1 15 2.5v3A1.5 1.5 0 0 1 13.5 7h-3A1.5 1.5 0 0 1 9 5.5v-3zm-8 8A1.5 1.5 0 0 1 2.5 9h3A1.5 1.5 0 0 1 7 10.5v3A1.5 1.5 0 0 1 5.5 15h-3A1.5 1.5 0 0 1 1 13.5v-3zm8 0A1.5 1.5 0 0 1 10.5 9h3a1.5 1.5 0 0 1 1.5 1.5v3a1.5 1.5 0 0 1-1.5 1.5h-3A1.5 1.5 0 0 1 9 13.5v-3z"/>
                    </svg>
                    Lagerstatus
                </button>
            </div>

            <!-- Tab 1: Opret Produkt -->
            <div id="product-tab-create" class="product-tab-content active">
                <div class="card">
                    <h3>Opret Nyt Produkt</h3>
                    <form id="createProductForm" class="admin-form">
                        <div class="form-group">
                            <label for="product-name">Produkt Navn *</label>
                            <input type="text" id="product-name" name="name" required>
                        </div>
                        <div class="form-group">
                            <label for="product-description">Beskrivelse</label>
                            <textarea id="product-description" name="description" rows="3"></textarea>
                        </div>
                        <div class="form-group">
                            <label for="product-price">Pris (kr) *</label>
                            <input type="number" step="0.01" id="product-price" name="price" required>
                        </div>
                        <div class="form-group">
                            <label for="product-size">Størrelse *</label>
                            <select id="product-size" name="size" required>
                                <option value="">Vælg størrelse</option>
                                <option value="XS">XS</option>
                                <option value="S">S</option>
                                <option value="M">M</option>
                                <option value="L">L</option>
                                <option value="XL">XL</option>
                                <option value="XXL">XXL</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="product-color">Farve *</label>
                            <select id="product-color" name="color" required>
                                <option value="">Vælg farve</option>
                                <option value="RED">Rød</option>
                                <option value="BLUE">Blå</option>
                                <option value="GREEN">Grøn</option>
                                <option value="BLACK">Sort</option>
                                <option value="WHITE">Hvid</option>
                                <option value="YELLOW">Gul</option>
                                <option value="ORANGE">Orange</option>
                                <option value="PURPLE">Lilla</option>
                                <option value="PINK">Pink</option>
                                <option value="GRAY">Grå</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="product-stock">Lager Antal *</label>
                            <input type="number" id="product-stock" name="stockQuantity" required min="0">
                        </div>
                        <div class="form-group">
                            <label for="product-image">Billede URL</label>
                            <input type="text" id="product-image" name="imageUrl" placeholder="https://...">
                        </div>
                        <div class="form-group">
                            <label class="checkbox-label">
                                <input type="checkbox" id="product-active" name="active" checked>
                                <span>Aktiv (synlig i butikken)</span>
                            </label>
                        </div>
                        <button type="submit" class="btn-primary">Opret Produkt</button>
                    </form>
                    <p id="product-message" class="status-message"></p>
                </div>
            </div>

            <!-- Tab 2: Lagerstatus -->
            <div id="product-tab-inventory" class="product-tab-content">
                <div class="card">
                    <h3>Lagerstatus - Alle Produkter</h3>
                    <div id="products-list"></div>
                </div>
            </div>
        </main>
    `,

    gifts: () => `
        <main class="admin-container">
            <div class="admin-header">
                <div style="display: flex; align-items: center; gap: 0.5rem;">
                    <svg width="40" height="40" viewBox="0 0 40 40" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <rect x="12" y="17" width="16" height="16" rx="1" fill="#e8f5e9" stroke="#2d5f2e" stroke-width="1.5"/>
                        <rect x="11" y="15" width="18" height="4" rx="0.5" fill="#4a8c4a"/>
                        <path d="M20 15 L20 33" stroke="#2d5f2e" stroke-width="1.5"/>
                        <path d="M20 15 Q17 10 15 13 Q16 15 20 15" fill="#dc3545"/>
                        <path d="M20 15 Q23 10 25 13 Q24 15 20 15" fill="#dc3545"/>
                    </svg>
                    <h1 style="margin: 0;">Gave Produkter Administration</h1>
                </div>
                <button class="btn-secondary" onclick="renderAdminSection('dashboard')">← Tilbage til Dashboard</button>
            </div>

            <!-- Navigation Tabs -->
            <div style="display: flex; gap: 0.5rem; margin-bottom: 2rem; border-bottom: 2px solid #e0e0e0; padding-bottom: 0;">
                <button class="gift-tab active" onclick="showGiftTab('threshold')" id="tab-threshold">
                    <svg width="18" height="18" viewBox="0 0 16 16" fill="currentColor" style="vertical-align: middle; margin-right: 4px;">
                        <circle cx="8" cy="8" r="6" stroke="currentColor" stroke-width="1.5" fill="none"/>
                        <text x="8" y="11" font-size="8" text-anchor="middle" fill="currentColor" font-weight="bold">kr</text>
                    </svg>
                    Gave Beløbsgrænse
                </button>
                <button class="gift-tab" onclick="showGiftTab('create')" id="tab-create">
                    <svg width="18" height="18" viewBox="0 0 16 16" fill="currentColor" style="vertical-align: middle; margin-right: 4px;">
                        <path d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z"/>
                    </svg>
                    Opret Gave Produkt
                </button>
                <button class="gift-tab" onclick="showGiftTab('inventory')" id="tab-inventory">
                    <svg width="18" height="18" viewBox="0 0 16 16" fill="currentColor" style="vertical-align: middle; margin-right: 4px;">
                        <path d="M1 2.5A1.5 1.5 0 0 1 2.5 1h3A1.5 1.5 0 0 1 7 2.5v3A1.5 1.5 0 0 1 5.5 7h-3A1.5 1.5 0 0 1 1 5.5v-3zm8 0A1.5 1.5 0 0 1 10.5 1h3A1.5 1.5 0 0 1 15 2.5v3A1.5 1.5 0 0 1 13.5 7h-3A1.5 1.5 0 0 1 9 5.5v-3zm-8 8A1.5 1.5 0 0 1 2.5 9h3A1.5 1.5 0 0 1 7 10.5v3A1.5 1.5 0 0 1 5.5 15h-3A1.5 1.5 0 0 1 1 13.5v-3zm8 0A1.5 1.5 0 0 1 10.5 9h3a1.5 1.5 0 0 1 1.5 1.5v3a1.5 1.5 0 0 1-1.5 1.5h-3A1.5 1.5 0 0 1 9 13.5v-3z"/>
                    </svg>
                    Lagerstatus
                </button>
            </div>

            <!-- Tab 1: Gave Beløbsgrænse -->
            <div id="gift-tab-threshold" class="gift-tab-content active">
                <div class="card">
                    <h3>
                        <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg" style="vertical-align: middle; margin-right: 8px;">
                            <rect x="6" y="10" width="12" height="12" rx="1" fill="#e8f5e9" stroke="#2d5f2e" stroke-width="1.5"/>
                            <rect x="5" y="8" width="14" height="3" rx="0.5" fill="#4a8c4a"/>
                            <path d="M12 8 L12 22" stroke="#2d5f2e" stroke-width="1.5"/>
                            <path d="M12 8 Q10 4 8 7 Q9 8 12 8" fill="#dc3545"/>
                            <path d="M12 8 Q14 4 16 7 Q15 8 12 8" fill="#dc3545"/>
                        </svg>
                        Gave Konfiguration - Administrer grænser
                    </h3>
                    <p style="color: #666; font-size: 0.9rem;">
                        <svg width="16" height="16" viewBox="0 0 16 16" fill="#4a8c4a" style="vertical-align: middle; margin-right: 4px;">
                            <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
                            <path d="M7.002 11a1 1 0 1 1 2 0 1 1 0 0 1-2 0zM7.1 4.995a.905.905 0 1 1 1.8 0l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 4.995z"/>
                        </svg>
                        <strong>Sådan virker det:</strong> Tilføj flere gave produkter til samme beløbsgrænse. 
                        Når kunden når grænsen, kan de <strong>vælge ÉN gave</strong> blandt de tilgængelige muligheder.
                    </p>
                    
                    <div id="thresholdGroups" style="margin-top: 1rem;">
                        <h4>
                            <svg width="20" height="20" viewBox="0 0 16 16" fill="#2d5f2e" style="vertical-align: middle; margin-right: 4px;">
                                <path d="M1 2.5A1.5 1.5 0 0 1 2.5 1h3A1.5 1.5 0 0 1 7 2.5v3A1.5 1.5 0 0 1 5.5 7h-3A1.5 1.5 0 0 1 1 5.5v-3zM2.5 2a.5.5 0 0 0-.5.5v3a.5.5 0 0 0 .5.5h3a.5.5 0 0 0 .5-.5v-3a.5.5 0 0 0-.5-.5h-3zm6.5.5A1.5 1.5 0 0 1 10.5 1h3A1.5 1.5 0 0 1 15 2.5v3A1.5 1.5 0 0 1 13.5 7h-3A1.5 1.5 0 0 1 9 5.5v-3zm1.5-.5a.5.5 0 0 0-.5.5v3a.5.5 0 0 0 .5.5h3a.5.5 0 0 0 .5-.5v-3a.5.5 0 0 0-.5-.5h-3zM1 10.5A1.5 1.5 0 0 1 2.5 9h3A1.5 1.5 0 0 1 7 10.5v3A1.5 1.5 0 0 1 5.5 15h-3A1.5 1.5 0 0 1 1 13.5v-3zm1.5-.5a.5.5 0 0 0-.5.5v3a.5.5 0 0 0 .5.5h3a.5.5 0 0 0 .5-.5v-3a.5.5 0 0 0-.5-.5h-3zm6.5.5A1.5 1.5 0 0 1 10.5 9h3a1.5 1.5 0 0 1 1.5 1.5v3a1.5 1.5 0 0 1-1.5 1.5h-3A1.5 1.5 0 0 1 9 13.5v-3zm1.5-.5a.5.5 0 0 0-.5.5v3a.5.5 0 0 0 .5.5h3a.5.5 0 0 0 .5-.5v-3a.5.5 0 0 0-.5-.5h-3z"/>
                            </svg>
                            Nuværende Konfigurationer:
                        </h4>
                        <div id="configList">Indlæser...</div>
                    </div>
                    
                    <hr style="margin: 2rem 0;">
                    
                    <h4>
                        <svg width="20" height="20" viewBox="0 0 16 16" fill="#2d5f2e" style="vertical-align: middle; margin-right: 4px;">
                            <path d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z"/>
                        </svg>
                        Tilføj Ny Gave Konfiguration
                    </h4>
                    <form id="giftConfigForm" class="admin-form">
                        <div class="form-group">
                            <label for="newAmount">Beløbsgrænse (kr.) *</label>
                            <input type="number" id="newAmount" name="thresholdAmount" placeholder="F.eks. 500" required min="0" step="0.01">
                            <small style="color: #666;">
                                <svg width="14" height="14" viewBox="0 0 16 16" fill="#666" style="vertical-align: middle; margin-right: 2px;">
                                    <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
                                    <path d="M7.002 11a1 1 0 1 1 2 0 1 1 0 0 1-2 0zM7.1 4.995a.905.905 0 1 1 1.8 0l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 4.995z"/>
                                </svg>
                                Produkter med samme beløb grupperes. Kunden vælger ÉN gave fra gruppen.
                            </small>
                        </div>
                        <div class="form-group">
                            <label for="giftProductSelect">Vælg gave produkt *</label>
                            <select id="giftProductSelect" name="giftProductId" required>
                                <option value="">-- Vælg et gave produkt --</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label class="checkbox-label">
                                <input type="checkbox" id="giftConfigActive" name="active" checked>
                                <span>Aktiv (gave ordning er slået til)</span>
                            </label>
                        </div>
                        <button type="submit" class="btn-primary">
                            <svg width="16" height="16" viewBox="0 0 16 16" fill="currentColor" style="vertical-align: middle; margin-right: 4px;">
                                <path d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z"/>
                            </svg>
                            Tilføj Gave til Grænse
                        </button>
                    </form>
                    <p id="threshold-message" class="status-message"></p>
                </div>
            </div>

            <!-- Tab 2: Opret Gave Produkt -->
            <div id="gift-tab-create" class="gift-tab-content">
                <div class="card">
                    <h3>Opret Nyt Gave Produkt</h3>
                    <form id="createGiftForm" class="admin-form">
                        <div class="form-group">
                            <label for="gift-name">Produkt Navn *</label>
                            <input type="text" id="gift-name" name="name" required>
                        </div>
                        <div class="form-group">
                            <label for="gift-description">Beskrivelse</label>
                            <textarea id="gift-description" name="description" rows="3"></textarea>
                        </div>
                        <div class="form-group">
                            <label for="gift-stock">Lager Antal *</label>
                            <input type="number" id="gift-stock" name="stockQuantity" required min="0">
                        </div>
                        <div class="form-group">
                            <label for="gift-image">Billede URL</label>
                            <input type="text" id="gift-image" name="imageUrl" placeholder="https://...">
                        </div>
                        <div class="form-group">
                            <label class="checkbox-label">
                                <input type="checkbox" id="gift-active" name="active" checked>
                                <span>Aktiv (synlig som gave)</span>
                            </label>
                        </div>
                        <button type="submit" class="btn-primary">Opret Gave Produkt</button>
                    </form>
                    <p id="gift-message" class="status-message"></p>
                </div>
            </div>

            <!-- Tab 3: Lagerstatus -->
            <div id="gift-tab-inventory" class="gift-tab-content">
                <div class="card">
                    <h3>Lagerstatus - Alle Gave Produkter</h3>
                    <div id="gifts-list"></div>
                </div>
            </div>
        </main>
    `,

    customers: () => `
        <main class="admin-container">
            <div class="admin-header">
                <div style="display: flex; align-items: center; gap: 0.5rem;">
                    <svg width="40" height="40" viewBox="0 0 40 40" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <circle cx="15" cy="15" r="4" fill="#4a8c4a"/>
                        <path d="M9 26C9 22 11 20 15 20C19 20 21 22 21 26" stroke="#2d5f2e" stroke-width="1.5" stroke-linecap="round"/>
                        <circle cx="25" cy="16" r="3.5" fill="#e8f5e9" stroke="#2d5f2e" stroke-width="1.5"/>
                        <path d="M20 27C20 24 22 22 25 22C28 22 30 24 30 27" stroke="#2d5f2e" stroke-width="1.5" stroke-linecap="round"/>
                    </svg>
                    <h1 style="margin: 0;">Kunde Administration</h1>
                </div>
                <button class="btn-secondary" onclick="renderAdminSection('dashboard')">← Tilbage til Dashboard</button>
            </div>

            <div class="card">
                <h3>Alle Kunder</h3>
                <div id="customers-list"></div>
            </div>
        </main>
    `
};

// Render admin section med subsection support
function renderAdminSection(subsection = 'dashboard') {
    // SIKKERHEDSCHECK: Verificer at brugeren er admin
    const userType = localStorage.getItem('userType');
    if (userType !== 'ADMIN') {
        alert('⛔ Adgang nægtet! Kun administratorer har adgang til admin panelet.');
        // Redirect til shop hvis ikke admin
        if (typeof renderSection === 'function') {
            renderSection('shop');
        }
        return; // Stop udførelse
    }
    
    const content = document.getElementById('app-content');
    if (content && adminSections[subsection]) {
        content.innerHTML = adminSections[subsection]();
        // Initialiser funktionalitet baseret på subsection
        initializeAdminSection(subsection);
    }
}

// Initialiser admin funktionalitet baseret på section
function initializeAdminSection(subsection) {
    switch(subsection) {
        case 'products':
            loadProductsAdmin();
            setupProductForm();
            break;
        case 'gifts':
            setupGiftThreshold();
            loadGiftProductsAdmin();
            setupGiftProductForm();
            break;
        case 'customers':
            loadCustomers();
            break;
    }
}

// ============ PRODUCTS CRUD ============
async function loadProductsAdmin() {
    try {
        const response = await fetch(PRODUCTS_API_URL);
        const products = await response.json();
        
        const productsList = document.getElementById('products-list');
        if (productsList) {
            // Opdel produkter i aktive og inaktive
            const activeProducts = products.filter(p => p.active === 1 || p.active === true);
            const inactiveProducts = products.filter(p => p.active === 0 || p.active === false);
            
            const renderProductTable = (productList, title) => {
                if (productList.length === 0) {
                    return `<p style="color: #666; font-style: italic;">Ingen ${title.toLowerCase()} produkter</p>`;
                }
                
                return `
                    <div class="product-item" style="margin-bottom: 1rem;">
                        ${productList.map(product => `
                            <div style="display: flex; justify-content: space-between; align-items: center; padding: 1rem; border-bottom: 1px solid #e0e0e0;">
                                <div class="product-item-info">
                                    <strong>${product.name}</strong>
                                    <p style="margin: 0.25rem 0; color: #666;">${product.description || 'Ingen beskrivelse'}</p>
                                    <p style="margin: 0.25rem 0; font-size: 0.9rem;">
                                        <strong>Pris:</strong> ${product.price} kr | 
                                        <strong>Størrelse:</strong> ${product.size} | 
                                        <strong>Farve:</strong> ${translateColor(product.color)}
                                    </p>
                                    <p style="margin: 0.25rem 0; font-size: 0.9rem;">
                                        <strong>Lager:</strong> ${product.stockQuantity} stk
                                    </p>
                                </div>
                                <div class="product-item-actions">
                                    <button class="btn-edit" onclick="editProduct(${product.id})">
                                        <svg width="16" height="16" viewBox="0 0 16 16" fill="currentColor" style="vertical-align: middle; margin-right: 4px;">
                                            <path d="M12.146.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1 0 .708l-10 10a.5.5 0 0 1-.168.11l-5 2a.5.5 0 0 1-.65-.65l2-5a.5.5 0 0 1 .11-.168l10-10zM11.207 2.5 13.5 4.793 14.793 3.5 12.5 1.207 11.207 2.5zm1.586 3L10.5 3.207 4 9.707V10h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.293l6.5-6.5zm-9.761 5.175-.106.106-1.528 3.821 3.821-1.528.106-.106A.5.5 0 0 1 5 12.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.468-.325z"/>
                                        </svg>
                                        Rediger
                                    </button>
                                    <button class="btn-delete" onclick="deleteProduct(${product.id})">
                                        <svg width="16" height="16" viewBox="0 0 16 16" fill="currentColor" style="vertical-align: middle; margin-right: 4px;">
                                            <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z"/>
                                            <path fill-rule="evenodd" d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z"/>
                                        </svg>
                                        Slet
                                    </button>
                                </div>
                            </div>
                        `).join('')}
                    </div>
                `;
            };
            
            productsList.innerHTML = `
                <div style="margin-bottom: 2rem;">
                    <h4 style="color: #4a8c4a; display: flex; align-items: center; gap: 0.5rem;">
                        <svg width="20" height="20" viewBox="0 0 16 16" fill="currentColor">
                            <path d="M10.97 4.97a.75.75 0 0 1 1.07 1.05l-3.99 4.99a.75.75 0 0 1-1.08.02L4.324 8.384a.75.75 0 1 1 1.06-1.06l2.094 2.093 3.473-4.425a.267.267 0 0 1 .02-.022z"/>
                        </svg>
                        Aktive Produkter (${activeProducts.length})
                    </h4>
                    ${renderProductTable(activeProducts, 'Aktive')}
                </div>
                
                <div>
                    <h4 style="color: #d32f2f; display: flex; align-items: center; gap: 0.5rem;">
                        <svg width="20" height="20" viewBox="0 0 16 16" fill="currentColor">
                            <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z"/>
                        </svg>
                        Inaktive Produkter (${inactiveProducts.length})
                    </h4>
                    ${renderProductTable(inactiveProducts, 'Inaktive')}
                </div>
            `;
        }
    } catch (error) {
        console.error('Fejl ved hentning af produkter:', error);
        showProductMessage('Kunne ikke hente produkter', 'red');
    }
}

// Hjælpefunktion til at oversætte farve enum til dansk
function translateColor(color) {
    const colorMap = {
        'RED': 'Rød',
        'BLUE': 'Blå',
        'GREEN': 'Grøn',
        'BLACK': 'Sort',
        'WHITE': 'Hvid',
        'YELLOW': 'Gul',
        'ORANGE': 'Orange',
        'PURPLE': 'Lilla',
        'PINK': 'Pink',
        'GRAY': 'Grå'
    };
    return colorMap[color] || color;
}

function setupProductForm() {
    const form = document.getElementById('createProductForm');
    if (form) {
        form.addEventListener('submit', async (e) => {
            e.preventDefault();
            const formData = new FormData(form);
            
            const productData = {
                name: formData.get('name'),
                description: formData.get('description') || null,
                price: parseFloat(formData.get('price')),
                imageUrl: formData.get('imageUrl') || null,
                size: formData.get('size'),
                color: formData.get('color'),
                stockQuantity: parseInt(formData.get('stockQuantity')),
                active: formData.get('active') === 'on'
            };
            
            // Validering
            if (!productData.name || !productData.price || !productData.size || !productData.color || productData.stockQuantity === null) {
                showProductMessage('Udfyld venligst alle påkrævede felter (*)', 'orange');
                return;
            }
            
            if (productData.price <= 0) {
                showProductMessage('Prisen skal være større end 0', 'orange');
                return;
            }
            
            if (productData.stockQuantity < 0) {
                showProductMessage('Lager antal kan ikke være negativt', 'orange');
                return;
            }
            
            try {
                // Check om vi er i edit mode
                const editId = form.dataset.editId;
                const method = editId ? 'PUT' : 'POST';
                const url = editId ? `${PRODUCTS_API_URL}/${editId}` : PRODUCTS_API_URL;
                
                const response = await fetch(url, {
                    method: method,
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(productData)
                });
                
                if (response.ok) {
                    showProductMessage(editId ? 'Produkt opdateret succesfuldt!' : 'Produkt oprettet succesfuldt!', 'green');
                    form.reset();
                    delete form.dataset.editId; // Fjern edit mode
                    
                    // Reset knap tekst
                    const submitBtn = form.querySelector('button[type="submit"]');
                    submitBtn.textContent = 'Opret Produkt';
                    
                    loadProductsAdmin();
                    
                    // Refresh products in main app
                    if (typeof loadProducts === 'function') {
                        await loadProducts();
                    }
                    
                    // Skift til inventory tab efter opdatering
                    if (editId) {
                        setTimeout(() => showProductTab('inventory'), 1000);
                    }
                } else {
                    showProductMessage(editId ? 'Fejl ved opdatering af produkt' : 'Fejl ved oprettelse af produkt', 'red');
                }
            } catch (error) {
                console.error('Fejl:', error);
                showProductMessage('Netværksfejl', 'red');
            }
        });
    }
}

async function deleteProduct(id) {
    if (!confirm('Er du sikker på at du vil slette dette produkt?')) return;
    
    try {
        const response = await fetch(`${PRODUCTS_API_URL}/${id}`, {
            method: 'DELETE'
        });
        
        if (response.ok) {
            showProductMessage('Produkt slettet!', 'green');
            loadProductsAdmin();
            
            // Refresh products in main app
            if (typeof loadProducts === 'function') {
                await loadProducts();
            }
        } else {
            showProductMessage('Fejl ved sletning', 'red');
        }
    } catch (error) {
        console.error('Fejl:', error);
        showProductMessage('Netværksfejl', 'red');
    }
}

async function editProduct(id) {
    try {
        // Hent produkt data
        const response = await fetch(`${PRODUCTS_API_URL}/${id}`);
        if (!response.ok) throw new Error('Kunne ikke hente produkt');
        const product = await response.json();

        // Skift til create tab
        showProductTab('create');

        // Vent lidt så tab'en kan blive synlig
        setTimeout(() => {
            // Udfyld form med eksisterende data
            document.getElementById('product-name').value = product.name || '';
            document.getElementById('product-description').value = product.description || '';
            document.getElementById('product-price').value = product.price || '';
            document.getElementById('product-size').value = product.size || '';
            document.getElementById('product-color').value = product.color || '';
            document.getElementById('product-stock').value = product.stockQuantity || '';
            document.getElementById('product-image').value = product.imageUrl || '';
            document.getElementById('product-active').checked = (product.active === 1 || product.active === true);

            // Opdater form til edit mode
            const form = document.getElementById('createProductForm');
            const submitBtn = form.querySelector('button[type="submit"]');
            submitBtn.textContent = 'Opdater Produkt';
            
            // Gem product ID for opdatering
            form.dataset.editId = id;
            
            // Scroll til toppen
            window.scrollTo({ top: 0, behavior: 'smooth' });
        }, 100);
        
    } catch (error) {
        console.error('Fejl ved edit af produkt:', error);
        showProductMessage('Kunne ikke hente produkt til redigering', 'red');
    }
}

function showProductMessage(text, color) {
    const msgElement = document.getElementById('product-message');
    if (msgElement) {
        msgElement.innerText = text;
        msgElement.style.color = color;
        setTimeout(() => msgElement.innerText = '', 3000);
    }
}

// ============ GIFT THRESHOLD ============
function setupGiftThreshold() {
    loadAllGiftConfigurations();
    loadGiftProductsForDropdown();
    
    const form = document.getElementById('giftConfigForm');
    if (form) {
        form.addEventListener('submit', async (e) => {
            e.preventDefault();
            await addGiftConfiguration();
        });
    }
}

// Hent og vis ALLE gift konfigurationer grupperet efter threshold
async function loadAllGiftConfigurations() {
    console.log('🔄 Henter gift konfigurationer fra:', `${API_URL}/all`);
    try {
        const response = await fetch(`${API_URL}/all`);
        console.log('📥 Response status:', response.status);
        
        if (!response.ok) {
            throw new Error(`HTTP ${response.status}: ${response.statusText}`);
        }
        
        const configs = await response.json();
        console.log('✅ Hentet konfigurationer:', configs);
        
        const configListDiv = document.getElementById('configList');
        
        if (!configListDiv) {
            console.error('❌ configList element ikke fundet!');
            return;
        }
        
        if (!configs || configs.length === 0) {
            configListDiv.innerHTML = '<p style="color: #999;">Ingen konfigurationer endnu. Tilføj den første gave nedenfor!</p>';
            return;
        }
        
        // Grupper efter threshold amount
        const grouped = configs.reduce((acc, config) => {
            const threshold = config.thresholdAmount;
            if (!acc[threshold]) {
                acc[threshold] = [];
            }
            acc[threshold].push(config);
            return acc;
        }, {});
        
        // Vis grupperede konfigurationer
        configListDiv.innerHTML = Object.entries(grouped)
            .sort(([a], [b]) => parseFloat(a) - parseFloat(b))
            .map(([threshold, configList]) => `
                <div style="border: 2px solid #4a8c4a; border-radius: 8px; padding: 1rem; margin-bottom: 1rem; background: #f8f9fa;">
                    <h5 style="margin: 0 0 0.5rem 0; color: #2d5f2e;">
                        <svg width="20" height="20" viewBox="0 0 20 20" fill="none" xmlns="http://www.w3.org/2000/svg" style="vertical-align: middle; margin-right: 4px;">
                            <circle cx="10" cy="10" r="8" stroke="#2d5f2e" stroke-width="1.5" fill="none"/>
                            <text x="10" y="14" font-size="10" text-anchor="middle" fill="#2d5f2e" font-weight="bold">kr</text>
                        </svg>
                        ${threshold} kr. grænse
                        <span style="font-size: 0.85rem; color: #666;">(${configList.length} valgmulighed${configList.length !== 1 ? 'er' : ''} - kunden vælger 1)</span>
                    </h5>
                    ${configList.map(config => `
                        <div style="display: flex; justify-content: space-between; align-items: center; padding: 0.5rem; background: white; border-radius: 4px; margin-bottom: 0.5rem;">
                            <div>
                                <strong>${config.giftProductName || 'Produkt ID: ' + config.giftProductId}</strong>
                                <span style="margin-left: 1rem; ${config.active === 1 ? 'color: green;' : 'color: red;'}">
                                    ${config.active === 1 ? '<svg width="16" height="16" viewBox="0 0 16 16" fill="currentColor" style="vertical-align: middle;"><path d="M13.854 3.646a.5.5 0 0 1 0 .708l-7 7a.5.5 0 0 1-.708 0l-3.5-3.5a.5.5 0 1 1 .708-.708L6.5 10.293l6.646-6.647a.5.5 0 0 1 .708 0z"/></svg> Aktiv' : '<svg width="16" height="16" viewBox="0 0 16 16" fill="currentColor" style="vertical-align: middle;"><path d="M2.146 2.854a.5.5 0 1 1 .708-.708L8 7.293l5.146-5.147a.5.5 0 0 1 .708.708L8.707 8l5.147 5.146a.5.5 0 0 1-.708.708L8 8.707l-5.146 5.147a.5.5 0 0 1-.708-.708L7.293 8 2.146 2.854Z"/></svg> Inaktiv'}
                                </span>
                            </div>
                            <div>
                                <button class="btn-edit" onclick="editGiftConfig(${config.id})" style="margin-right: 0.5rem;">
                                    <svg width="14" height="14" viewBox="0 0 16 16" fill="currentColor" style="vertical-align: middle;">
                                        <path d="M12.146.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1 0 .708l-10 10a.5.5 0 0 1-.168.11l-5 2a.5.5 0 0 1-.65-.65l2-5a.5.5 0 0 1 .11-.168l10-10zM11.207 2.5 13.5 4.793 14.793 3.5 12.5 1.207 11.207 2.5zm1.586 3L10.5 3.207 4 9.707V10h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.293l6.5-6.5zm-9.761 5.175-.106.106-1.528 3.821 3.821-1.528.106-.106A.5.5 0 0 1 5 12.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.468-.325z"/>
                                    </svg>
                                </button>
                                <button class="btn-delete" onclick="deleteGiftConfig(${config.id})">
                                    <svg width="14" height="14" viewBox="0 0 16 16" fill="currentColor" style="vertical-align: middle;">
                                        <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z"/>
                                        <path fill-rule="evenodd" d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z"/>
                                    </svg>
                                </button>
                            </div>
                        </div>
                    `).join('')}
                </div>
            `).join('');
        
    } catch (error) {
        console.error('❌ Fejl ved hentning af konfigurationer:', error);
        const configListDiv = document.getElementById('configList');
        if (configListDiv) {
            configListDiv.innerHTML = `<p style="color: red;">⚠️ Kunne ikke indlæse konfigurationer: ${error.message}</p>`;
        }
    }
}

// Hent gift produkter til dropdown
async function loadGiftProductsForDropdown() {
    console.log('🔄 Henter gift produkter fra:', GIFT_PRODUCTS_API_URL);
    try {
        const response = await fetch(GIFT_PRODUCTS_API_URL);
        console.log('📥 Gift products response status:', response.status);
        
        const giftProducts = await response.json();
        console.log('✅ Hentet gift produkter:', giftProducts);
        
        const selectElement = document.getElementById('giftProductSelect');
        if (selectElement) {
            selectElement.innerHTML = '<option value="">-- Vælg et gave produkt --</option>' +
                giftProducts
                    .filter(gift => gift.active) // Kun aktive produkter
                    .map(gift => 
                        `<option value="${gift.id}">${gift.name} (Lager: ${gift.stockQuantity})</option>`
                    ).join('');
            console.log('✅ Dropdown opdateret med', giftProducts.filter(g => g.active).length, 'aktive produkter');
        }
    } catch (error) {
        console.error('❌ Fejl ved hentning af gave produkter:', error);
    }
}

// Tilføj ny gift configuration (POST i stedet for PUT)
async function addGiftConfiguration() {
    const form = document.getElementById('giftConfigForm');
    const formData = new FormData(form);
    
    const thresholdAmount = parseFloat(formData.get('thresholdAmount'));
    const giftProductId = parseInt(formData.get('giftProductId'));
    const active = formData.get('active') === 'on' ? 1 : 0;

    // Validering
    if (!thresholdAmount || isNaN(thresholdAmount) || thresholdAmount <= 0) {
        showThresholdMessage("Indtast venligst et gyldigt beløb større end 0.", "orange");
        return;
    }
    
    if (!giftProductId || isNaN(giftProductId)) {
        showThresholdMessage("Vælg venligst et gave produkt.", "orange");
        return;
    }

    const configData = {
        thresholdAmount: thresholdAmount,
        giftProductId: giftProductId,
        active: active
    };

    console.log('📤 Tilføjer ny gift config:', configData);

    try {
        const response = await fetch(API_URL, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(configData)
        });
        
        console.log('📥 Backend response status:', response.status);

        if (response.ok) {
            showThresholdMessage("✅ Gave konfiguration tilføjet!", "green");
            form.reset();
            // Reload listen
            await loadAllGiftConfigurations();
        } else {
            const errorText = await response.text();
            showThresholdMessage("Fejl: " + errorText, "red");
        }

    } catch (error) {
        console.error("Netværksfejl:", error);
        showThresholdMessage("Der skete en teknisk fejl.", "red");
    }
}

// Slet en gift configuration
async function deleteGiftConfig(configId) {
    if (!confirm('Er du sikker på at du vil fjerne denne gave konfiguration?')) {
        return;
    }
    
    try {
        const response = await fetch(`${API_URL}/${configId}`, {
            method: 'DELETE'
        });
        
        if (response.ok) {
            showThresholdMessage('✅ Konfiguration fjernet!', 'green');
            await loadAllGiftConfigurations();
        } else {
            showThresholdMessage('Fejl ved sletning', 'red');
        }
    } catch (error) {
        console.error('Fejl:', error);
        showThresholdMessage('Netværksfejl', 'red');
    }
}

// Edit gift configuration (TODO)
function editGiftConfig(configId) {
    alert('Edit funktionalitet kommer snart! (ID: ' + configId + ')');
}

function showThresholdMessage(text, color) {
    const msgElement = document.getElementById("threshold-message");
    if (msgElement) {
        msgElement.innerText = text;
        msgElement.style.color = color;
        setTimeout(() => msgElement.innerText = '', 3000);
    }
}

// ============ GIFT PRODUCTS CRUD ============
async function loadGiftProductsAdmin() {
    try {
        const response = await fetch(GIFT_PRODUCTS_API_URL);
        const giftProducts = await response.json();
        
        const giftsList = document.getElementById('gifts-list');
        if (giftsList) {
            // Opdel gave produkter i aktive og inaktive
            const activeGifts = giftProducts.filter(g => g.active === 1 || g.active === true);
            const inactiveGifts = giftProducts.filter(g => g.active === 0 || g.active === false);
            
            const renderGiftTable = (giftList, title) => {
                if (giftList.length === 0) {
                    return `<p style="color: #666; font-style: italic;">Ingen ${title.toLowerCase()} gave produkter</p>`;
                }
                
                return `
                    <div class="product-item" style="margin-bottom: 1rem;">
                        ${giftList.map(gift => `
                            <div style="display: flex; justify-content: space-between; align-items: center; padding: 1rem; border-bottom: 1px solid #e0e0e0;">
                                <div class="product-item-info">
                                    <strong>${gift.name}</strong>
                                    <p style="margin: 0.25rem 0; color: #666;">${gift.description || 'Ingen beskrivelse'}</p>
                                    <p style="margin: 0.25rem 0; font-size: 0.9rem;">
                                        <strong>Lager:</strong> ${gift.stockQuantity} stk
                                    </p>
                                </div>
                                <div class="product-item-actions">
                                    <button class="btn-edit" onclick="editGiftProduct(${gift.id})">
                                        <svg width="16" height="16" viewBox="0 0 16 16" fill="currentColor" style="vertical-align: middle; margin-right: 4px;">
                                            <path d="M12.146.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1 0 .708l-10 10a.5.5 0 0 1-.168.11l-5 2a.5.5 0 0 1-.65-.65l2-5a.5.5 0 0 1 .11-.168l10-10zM11.207 2.5 13.5 4.793 14.793 3.5 12.5 1.207 11.207 2.5zm1.586 3L10.5 3.207 4 9.707V10h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.293l6.5-6.5zm-9.761 5.175-.106.106-1.528 3.821 3.821-1.528.106-.106A.5.5 0 0 1 5 12.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.468-.325z"/>
                                        </svg>
                                        Rediger
                                    </button>
                                    <button class="btn-delete" onclick="deleteGiftProduct(${gift.id})">
                                        <svg width="16" height="16" viewBox="0 0 16 16" fill="currentColor" style="vertical-align: middle; margin-right: 4px;">
                                            <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z"/>
                                            <path fill-rule="evenodd" d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z"/>
                                        </svg>
                                        Slet
                                    </button>
                                </div>
                            </div>
                        `).join('')}
                    </div>
                `;
            };
            
            giftsList.innerHTML = `
                <div style="margin-bottom: 2rem;">
                    <h4 style="color: #4a8c4a; display: flex; align-items: center; gap: 0.5rem;">
                        <svg width="20" height="20" viewBox="0 0 16 16" fill="currentColor">
                            <path d="M10.97 4.97a.75.75 0 0 1 1.07 1.05l-3.99 4.99a.75.75 0 0 1-1.08.02L4.324 8.384a.75.75 0 1 1 1.06-1.06l2.094 2.093 3.473-4.425a.267.267 0 0 1 .02-.022z"/>
                        </svg>
                        Aktive Gave Produkter (${activeGifts.length})
                    </h4>
                    ${renderGiftTable(activeGifts, 'Aktive')}
                </div>
                
                <div>
                    <h4 style="color: #d32f2f; display: flex; align-items: center; gap: 0.5rem;">
                        <svg width="20" height="20" viewBox="0 0 16 16" fill="currentColor">
                            <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z"/>
                        </svg>
                        Inaktive Gave Produkter (${inactiveGifts.length})
                    </h4>
                    ${renderGiftTable(inactiveGifts, 'Inaktive')}
                </div>
            `;
        }
    } catch (error) {
        console.error('Fejl ved hentning af gave produkter:', error);
        showGiftMessage('Kunne ikke hente gave produkter', 'red');
    }
}

function setupGiftProductForm() {
    const form = document.getElementById('createGiftForm');
    if (form) {
        form.addEventListener('submit', async (e) => {
            e.preventDefault();
            const formData = new FormData(form);
            
            const giftData = {
                name: formData.get('name'),
                description: formData.get('description') || null,
                imageUrl: formData.get('imageUrl') || null,
                stockQuantity: parseInt(formData.get('stockQuantity')),
                active: formData.get('active') === 'on'
            };
            
            // Validering
            if (!giftData.name || giftData.stockQuantity === null) {
                showGiftMessage('Udfyld venligst alle påkrævede felter (*)', 'orange');
                return;
            }
            
            if (giftData.stockQuantity < 0) {
                showGiftMessage('Lager antal kan ikke være negativt', 'orange');
                return;
            }
            
            try {
                // Check om vi er i edit mode
                const editId = form.dataset.editId;
                const method = editId ? 'PUT' : 'POST';
                const url = editId ? `${GIFT_PRODUCTS_API_URL}/${editId}` : GIFT_PRODUCTS_API_URL;
                
                const response = await fetch(url, {
                    method: method,
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(giftData)
                });
                
                if (response.ok) {
                    showGiftMessage(editId ? 'Gave produkt opdateret succesfuldt!' : 'Gave produkt oprettet succesfuldt!', 'green');
                    form.reset();
                    delete form.dataset.editId; // Fjern edit mode
                    
                    // Reset knap tekst
                    const submitBtn = form.querySelector('button[type="submit"]');
                    submitBtn.textContent = 'Opret Gave Produkt';
                    
                    loadGiftProductsAdmin();
                    
                    // Skift til inventory tab efter opdatering
                    if (editId) {
                        setTimeout(() => showGiftTab('inventory'), 1000);
                    }
                } else {
                    showGiftMessage(editId ? 'Fejl ved opdatering af gave produkt' : 'Fejl ved oprettelse af gave produkt', 'red');
                }
            } catch (error) {
                console.error('Fejl:', error);
                showGiftMessage('Netværksfejl', 'red');
            }
        });
    }
}

async function deleteGiftProduct(id) {
    if (!confirm('Er du sikker på at du vil slette dette gave produkt?')) return;
    
    try {
        const response = await fetch(`${GIFT_PRODUCTS_API_URL}/${id}`, {
            method: 'DELETE'
        });
        
        if (response.ok) {
            showGiftMessage('Gave produkt slettet!', 'green');
            loadGiftProductsAdmin();
        } else {
            showGiftMessage('Fejl ved sletning', 'red');
        }
    } catch (error) {
        console.error('Fejl:', error);
        showGiftMessage('Netværksfejl', 'red');
    }
}

async function editGiftProduct(id) {
    try {
        // Hent gave produkt data
        const response = await fetch(`${GIFT_PRODUCTS_API_URL}/${id}`);
        if (!response.ok) throw new Error('Kunne ikke hente gave produkt');
        const giftProduct = await response.json();

        // Skift til create tab
        showGiftTab('create');

        // Vent lidt så tab'en kan blive synlig
        setTimeout(() => {
            // Udfyld form med eksisterende data
            document.getElementById('gift-name').value = giftProduct.name || '';
            document.getElementById('gift-description').value = giftProduct.description || '';
            document.getElementById('gift-stock').value = giftProduct.stockQuantity || '';
            document.getElementById('gift-image').value = giftProduct.imageUrl || '';
            document.getElementById('gift-active').checked = (giftProduct.active === 1 || giftProduct.active === true);

            // Opdater form til edit mode
            const form = document.getElementById('createGiftForm');
            const submitBtn = form.querySelector('button[type="submit"]');
            submitBtn.textContent = 'Opdater Gave Produkt';
            
            // Gem gift product ID for opdatering
            form.dataset.editId = id;
            
            // Scroll til toppen
            window.scrollTo({ top: 0, behavior: 'smooth' });
        }, 100);
        
    } catch (error) {
        console.error('Fejl ved edit af gave produkt:', error);
        showGiftMessage('Kunne ikke hente gave produkt til redigering', 'red');
    }
}

function showGiftMessage(text, color) {
    const msgElement = document.getElementById('gift-message');
    if (msgElement) {
        msgElement.innerText = text;
        msgElement.style.color = color;
        setTimeout(() => msgElement.innerText = '', 3000);
    }
}

// ============ CUSTOMERS CRUD ============
async function loadCustomers() {
    try {
        const response = await fetch(CUSTOMERS_API_URL);
        const customers = await response.json();
        
        const customersList = document.getElementById('customers-list');
        if (customersList) {
            customersList.innerHTML = `
                <table class="admin-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Navn</th>
                            <th>Email</th>
                            <th>Handlinger</th>
                        </tr>
                    </thead>
                    <tbody>
                        ${customers.map(customer => `
                            <tr>
                                <td>${customer.id || customer.customerId}</td>
                                <td>${customer.name || customer.firstname + ' ' + customer.lastname}</td>
                                <td>${customer.email}</td>
                                <td>
                                    <button class="btn-edit-small" onclick="editCustomer(${customer.id || customer.customerId})">
                                        <svg width="14" height="14" viewBox="0 0 16 16" fill="currentColor">
                                            <path d="M12.146.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1 0 .708l-10 10a.5.5 0 0 1-.168.11l-5 2a.5.5 0 0 1-.65-.65l2-5a.5.5 0 0 1 .11-.168l10-10zM11.207 2.5 13.5 4.793 14.793 3.5 12.5 1.207 11.207 2.5zm1.586 3L10.5 3.207 4 9.707V10h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.293l6.5-6.5zm-9.761 5.175-.106.106-1.528 3.821 3.821-1.528.106-.106A.5.5 0 0 1 5 12.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.468-.325z"/>
                                        </svg>
                                    </button>
                                    <button class="btn-delete-small" onclick="deleteCustomer(${customer.id || customer.customerId})">
                                        <svg width="14" height="14" viewBox="0 0 16 16" fill="currentColor">
                                            <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z"/>
                                            <path fill-rule="evenodd" d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z"/>
                                        </svg>
                                    </button>
                                </td>
                            </tr>
                        `).join('')}
                    </tbody>
                </table>
            `;
        }
    } catch (error) {
        console.error('Fejl ved hentning af kunder:', error);
        const customersList = document.getElementById('customers-list');
        if (customersList) {
            customersList.innerHTML = '<p style="color: red;">Kunne ikke hente kunder</p>';
        }
    }
}

async function deleteCustomer(id) {
    if (!confirm('Er du sikker på at du vil slette denne kunde?')) return;
    
    try {
        const response = await fetch(`${CUSTOMERS_API_URL}/${id}`, {
            method: 'DELETE'
        });
        
        if (response.ok) {
            alert('Kunde slettet!');
            loadCustomers();
        } else {
            alert('Fejl ved sletning');
        }
    } catch (error) {
        console.error('Fejl:', error);
        alert('Netværksfejl');
    }
}

async function editCustomer(id) {
    try {
        // Hent kunde data
        const response = await fetch(`${CUSTOMERS_API_URL}/${id}`);
        if (!response.ok) throw new Error('Kunne ikke hente kunde');
        const customer = await response.json();

        // Opret modal dialog
        const modal = document.createElement('div');
        modal.style.cssText = `
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.5);
            display: flex;
            justify-content: center;
            align-items: center;
            z-index: 1000;
        `;
        
        modal.innerHTML = `
            <div style="background: white; padding: 2rem; border-radius: 8px; max-width: 500px; width: 90%;">
                <h3 style="margin-top: 0;">Rediger Kunde</h3>
                <form id="editCustomerForm">
                    <div style="margin-bottom: 1rem;">
                        <label style="display: block; margin-bottom: 0.5rem; font-weight: 500;">Fornavn *</label>
                        <input type="text" id="edit-firstname" value="${customer.firstname || customer.name?.split(' ')[0] || ''}" 
                               style="width: 100%; padding: 0.5rem; border: 1px solid #ddd; border-radius: 4px;" required>
                    </div>
                    <div style="margin-bottom: 1rem;">
                        <label style="display: block; margin-bottom: 0.5rem; font-weight: 500;">Efternavn *</label>
                        <input type="text" id="edit-lastname" value="${customer.lastname || customer.name?.split(' ')[1] || ''}" 
                               style="width: 100%; padding: 0.5rem; border: 1px solid #ddd; border-radius: 4px;" required>
                    </div>
                    <div style="margin-bottom: 1rem;">
                        <label style="display: block; margin-bottom: 0.5rem; font-weight: 500;">Email *</label>
                        <input type="email" id="edit-email" value="${customer.email || ''}" 
                               style="width: 100%; padding: 0.5rem; border: 1px solid #ddd; border-radius: 4px;" required>
                    </div>
                    <div style="margin-bottom: 1rem;">
                        <label style="display: block; margin-bottom: 0.5rem; font-weight: 500;">Telefon</label>
                        <input type="text" id="edit-phone" value="${customer.phone || ''}" 
                               style="width: 100%; padding: 0.5rem; border: 1px solid #ddd; border-radius: 4px;">
                    </div>
                    <div style="margin-bottom: 1rem;">
                        <label style="display: block; margin-bottom: 0.5rem; font-weight: 500;">Adresse</label>
                        <input type="text" id="edit-address" value="${customer.address || ''}" 
                               style="width: 100%; padding: 0.5rem; border: 1px solid #ddd; border-radius: 4px;">
                    </div>
                    <div style="display: flex; gap: 1rem; justify-content: flex-end; margin-top: 1.5rem;">
                        <button type="button" class="btn-secondary" onclick="this.closest('.modal-wrapper').remove()">Annuller</button>
                        <button type="submit" class="btn-primary">Gem Ændringer</button>
                    </div>
                </form>
            </div>
        `;
        
        modal.className = 'modal-wrapper';
        document.body.appendChild(modal);
        
        // Håndter form submission
        const form = document.getElementById('editCustomerForm');
        form.addEventListener('submit', async (e) => {
            e.preventDefault();
            
            const updatedCustomer = {
                firstname: document.getElementById('edit-firstname').value,
                lastname: document.getElementById('edit-lastname').value,
                email: document.getElementById('edit-email').value,
                phone: document.getElementById('edit-phone').value,
                address: document.getElementById('edit-address').value
            };
            
            try {
                const updateResponse = await fetch(`${CUSTOMERS_API_URL}/${id}`, {
                    method: 'PUT',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(updatedCustomer)
                });
                
                if (updateResponse.ok) {
                    alert('Kunde opdateret succesfuldt!');
                    modal.remove();
                    loadCustomers();
                } else {
                    alert('Fejl ved opdatering af kunde');
                }
            } catch (error) {
                console.error('Fejl:', error);
                alert('Netværksfejl');
            }
        });
        
        // Luk modal ved klik udenfor
        modal.addEventListener('click', (e) => {
            if (e.target === modal) {
                modal.remove();
            }
        });
        
    } catch (error) {
        console.error('Fejl ved edit af kunde:', error);
        alert('Kunne ikke hente kunde til redigering');
    }
}

// ============ TAB NAVIGATION ============
function showGiftTab(tabName) {
    // Skjul alle tabs
    document.querySelectorAll('.gift-tab-content').forEach(tab => {
        tab.classList.remove('active');
    });
    
    // Fjern active fra alle tab buttons
    document.querySelectorAll('.gift-tab').forEach(btn => {
        btn.classList.remove('active');
    });
    
    // Vis den valgte tab
    const selectedTab = document.getElementById(`gift-tab-${tabName}`);
    if (selectedTab) {
        selectedTab.classList.add('active');
    }
    
    // Aktiver den valgte tab button
    const selectedBtn = document.getElementById(`tab-${tabName}`);
    if (selectedBtn) {
        selectedBtn.classList.add('active');
    }
}

function showProductTab(tabName) {
    // Skjul alle product tabs
    document.querySelectorAll('.product-tab-content').forEach(tab => {
        tab.classList.remove('active');
    });
    
    // Fjern active fra alle product tab buttons
    document.querySelectorAll('.product-tab').forEach(btn => {
        btn.classList.remove('active');
    });
    
    // Vis den valgte tab
    const selectedTab = document.getElementById(`product-tab-${tabName}`);
    if (selectedTab) {
        selectedTab.classList.add('active');
    }
    
    // Aktiver den valgte tab button
    const selectedBtn = document.getElementById(`tab-product-${tabName}`);
    if (selectedBtn) {
        selectedBtn.classList.add('active');
    }
}
