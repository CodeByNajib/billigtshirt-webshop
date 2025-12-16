const API_BASE_URL = '/api'; 

const api = {
    async getProducts() {
        try {
            const response = await fetch(`${API_BASE_URL}/products`);
            if (!response.ok) throw new Error('Kunne ikke hente produkter');
            return await response.json();
        } catch (error) {
            console.error('Fejl ved hentning af produkter:', error);
            return [];
        }
    },

    async getProductById(id) {
        try {
            const response = await fetch(`${API_BASE_URL}/products/${id}`);
            if (!response.ok) throw new Error('Kunne ikke hente produkt');
            return await response.json();
        } catch (error) {
            console.error('Fejl ved hentning af produkt:', error);
            return null;
        }
    },

    async addToCart(productId, quantity = 1) {
        try {
            const token = localStorage.getItem('authToken');
            const requestBody = { productId, quantity };
            
            console.log('🛒 Adding to cart:', requestBody);
            
            const response = await fetch(`${API_BASE_URL}/cart`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': token ? `Bearer ${token}` : ''
                },
                credentials: 'include',
                body: JSON.stringify(requestBody)
            });
            
            console.log('Cart response status:', response.status);
            
            if (!response.ok) {
                const errorData = await response.json().catch(() => null);
                console.error('❌ Backend error:', errorData);
                throw new Error(errorData?.message || 'Kunne ikke tilføje til kurv');
            }
            
            const result = await response.json();
            console.log('✅ Added to cart:', result);
            return result;
        } catch (error) {
            console.error('Fejl ved tilføjelse til kurv:', error);
            return null;
        }
    },

    async getCart() {
        try {
            const response = await fetch(`${API_BASE_URL}/cart`, {
                credentials: 'include'  // VIGTIGT: Send session cookie!
            });
            if (!response.ok) throw new Error('Kunne ikke hente kurv');
            return await response.json();
        } catch (error) {
            console.error('Fejl ved hentning af kurv:', error);
            return { items: [], grandTotal: 0 };  // Return korrekt struktur
        }
    },

    async updateCartItemQuantity(productId, quantity) {
        try {
            console.log('Opdaterer kurv:', { productId, quantity });
            const response = await fetch(`${API_BASE_URL}/cart/update`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                credentials: 'include',
                body: JSON.stringify({ productId, quantity })
            });
            
            console.log('Update response status:', response.status);
            
            if (!response.ok) {
                const errorData = await response.json().catch(() => null);
                console.error('Backend fejl ved opdatering:', errorData);
                throw new Error(errorData?.message || `HTTP ${response.status}`);
            }
            
            const result = await response.json();
            console.log('Kurv opdateret succesfuldt:', result);
            return result;
        } catch (error) {
            console.error('Fejl ved opdatering af kurv:', error);
            return null;
        }
    },

    async removeCartItem(productId) {
        try {
            console.log('Fjerner produkt:', productId);
            const response = await fetch(`${API_BASE_URL}/cart/remove/${productId}`, {
                method: 'DELETE',
                credentials: 'include'
            });
            
            console.log('Remove response status:', response.status);
            
            if (!response.ok) {
                const errorData = await response.json().catch(() => null);
                console.error('Backend fejl ved fjernelse:', errorData);
                throw new Error(errorData?.message || `HTTP ${response.status}`);
            }
            
            const result = await response.json();
            console.log('Produkt fjernet succesfuldt:', result);
            return result;
        } catch (error) {
            console.error('Fejl ved fjernelse af produkt:', error);
            return null;
        }
    },

    async login(email, password) {
        try {
            console.log('🔐 Attempting login:', email);
            const response = await fetch(`${API_BASE_URL}/auth/login`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                credentials: 'include', // Tillad cookies hvis backend bruger dem
                body: JSON.stringify({ email, password })
            });
            
            console.log('Login response status:', response.status);
            
            if (!response.ok) {
                const errorData = await response.json().catch(() => null);
                console.error('Login error:', errorData);
                throw new Error(errorData?.message || 'Login fejlede');
            }
            
            const data = await response.json();
            console.log('✅ Login successful:', data);
            
            // Gem token hvis backend sender en
            if (data.token) {
                localStorage.setItem('authToken', data.token);
            }
            
            return data;
        } catch (error) {
            console.error('Login fejl:', error);
            return null;
        }
    },

    async signup(userData) {
        try {
            const response = await fetch(`${API_BASE_URL}/auth/signup`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                mode: 'cors',
                credentials: 'include',
                body: JSON.stringify(userData)
            });
            
            if (!response.ok) {
                const errorData = await response.json().catch(() => null);
                throw new Error(errorData?.message || 'Registrering fejlede');
            }
            
            return await response.json();
        } catch (error) {
            console.error('Registrering fejl:', error);
            return null;
        }
    },

    async createOrder(orderData) {
        try {
            const token = localStorage.getItem('authToken');
            const response = await fetch(`${API_BASE_URL}/orders`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': token ? `Bearer ${token}` : ''
                },
                body: JSON.stringify(orderData)
            });
            if (!response.ok) throw new Error('Kunne ikke oprette ordre');
            return await response.json();
        } catch (error) {
            console.error('Fejl ved oprettelse af ordre:', error);
            return null;
        }
    },

    async getOrders() {
        try {
            const token = localStorage.getItem('authToken');
            const response = await fetch(`${API_BASE_URL}/orders`, {
                headers: {
                    'Authorization': token ? `Bearer ${token}` : ''
                }
            });
            if (!response.ok) throw new Error('Kunne ikke hente ordrer');
            return await response.json();
        } catch (error) {
            console.error('Fejl ved hentning af ordrer:', error);
            return [];
        }
    },

    // Place order with gift validation
    async placeOrder(orderData) {
        try {
            console.log('Placerer ordre:', orderData);
            const response = await fetch(`${API_BASE_URL}/orders/checkout`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                credentials: 'include',
                body: JSON.stringify(orderData)
            });
            
            console.log('Checkout response status:', response.status);
            
            if (!response.ok) {
                const errorData = await response.json().catch(() => null);
                console.error('Backend fejl ved checkout:', errorData);
                
                // Helpful error message for development
                if (response.status === 500) {
                    throw new Error('Backend endpoint ikke implementeret endnu. Se BACKEND_CHECKOUT_PROMPT.md for implementationsdetaljer.');
                }
                
                throw new Error(errorData?.message || `HTTP ${response.status}`);
            }
            
            const result = await response.json();
            console.log('Ordre oprettet:', result);
            return result;
            
        } catch (error) {
            console.error('Fejl ved ordre oprettelse:', error);
            throw error;
        }
    },

    // TASK 2.1: Get available gift products
    async getGiftProducts() {
        try {
            const response = await fetch(`${API_BASE_URL}/gift-products`);
            if (!response.ok) throw new Error('Kunne ikke hente gaveprodukter');
            return await response.json();
        } catch (error) {
            console.error('Fejl ved hentning af gaveprodukter:', error);
            return [];
        }
    },

    // TASK 2.3: Select a free gift
    async selectFreeGift(giftProductId) {
        try {
            console.log('Sender gave valg til backend:', giftProductId);
            const response = await fetch(`${API_BASE_URL}/cart/select-free-gift`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                credentials: 'include',
                body: JSON.stringify({ giftProductId })
            });
            
            console.log('Response status:', response.status);
            
            if (!response.ok) {
                const errorData = await response.json().catch(() => null);
                const errorMsg = errorData?.message || `HTTP ${response.status}: ${response.statusText}`;
                console.error('Backend fejl:', errorData);
                throw new Error(errorMsg);
            }
            
            const result = await response.json();
            console.log('Gave valgt succesfuldt:', result);
            return result;
        } catch (error) {
            console.error('Fejl ved valg af gave - Details:', {
                message: error.message,
                stack: error.stack,
                error: error
            });
            throw error;
        }
    }
};
