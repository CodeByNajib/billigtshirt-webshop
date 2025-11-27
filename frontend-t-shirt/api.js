const API_BASE_URL = 'http://localhost:8080/api'; 

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
            const response = await fetch(`${API_BASE_URL}/cart`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ productId, quantity })
            });
            if (!response.ok) throw new Error('Kunne ikke tilføje til kurv');
            return await response.json();
        } catch (error) {
            console.error('Fejl ved tilføjelse til kurv:', error);
            return null;
        }
    },

    async getCart() {
        try {
            const response = await fetch(`${API_BASE_URL}/cart`);
            if (!response.ok) throw new Error('Kunne ikke hente kurv');
            return await response.json();
        } catch (error) {
            console.error('Fejl ved hentning af kurv:', error);
            return [];
        }
    },

    async login(email, password) {
        try {
            const response = await fetch(`${API_BASE_URL}/auth/login`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ email, password })
            });
            if (!response.ok) throw new Error('Login fejlede');
            const data = await response.json();
            //Gem token hvis backend sender en
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
                body: JSON.stringify(userData)
            });
            if (!response.ok) throw new Error('Registrering fejlede');
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
    }
};
