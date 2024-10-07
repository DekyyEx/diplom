const cart = JSON.parse(localStorage.getItem('cart')) || [];

document.addEventListener('DOMContentLoaded', () => {
    const addToCartButtons = document.querySelectorAll('.add-to-cart');

    addToCartButtons.forEach(button => {
        button.addEventListener('click', () => {
            const bookTitle = button.dataset.title;
            const bookPrice = parseFloat(button.dataset.price);
            addToCart(bookTitle, bookPrice);
        });
    });

    const orderForm = document.getElementById('orderForm');
    if (orderForm) {
        orderForm.addEventListener('submit', handleOrderSubmit);
    }

    updateCartUI();
});

function addToCart(title, price) {
    const existingItem = cart.find(item => item.title === title);
    if (existingItem) {
        existingItem.quantity += 1;
    } else {
        cart.push({ title, price, quantity: 1 });
    }

    localStorage.setItem('cart', JSON.stringify(cart));
    alert(`${title} добавлена в корзину!`);
    updateCartUI();
}

function updateCartUI() {
    const cartCount = document.getElementById('cart-count');
    const cartTotal = document.getElementById('cart-total');
    const cartList = document.getElementById('cart-list');

    const totalItems = cart.reduce((acc, item) => acc + item.quantity, 0);
    const totalPrice = cart.reduce((acc, item) => acc + item.price * item.quantity, 0).toFixed(2);

    if (cartCount) {
        cartCount.textContent = totalItems;
    }

    if (cartTotal) {
        cartTotal.textContent = `$${totalPrice}`;
    }

    if (cartList) {
        cartList.innerHTML = '';
        cart.forEach(item => {
            const listItem = document.createElement('li');
            listItem.textContent = `${item.title} (x${item.quantity}) - $${(item.price * item.quantity).toFixed(2)}`;
            cartList.appendChild(listItem);
        });
    }
}

async function handleOrderSubmit(event) {
    event.preventDefault();

    const name = event.target.name.value;
    const address = event.target.address.value;
    const pickupLocation = event.target.pickupLocation.value;

    if (cart.length === 0) {
        alert('Ваша корзина пуста!');
        return;
    }

    if (!name || !address || !pickupLocation) {
        alert('Пожалуйста, заполните все поля формы.');
        return;
    }

    const orderData = {
        name,
        address,
        pickupLocation,
        items: cart
    };

    try {
        const response = await fetch('/api/v1/orders', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(orderData)
        });

        if (!response.ok) {
            throw new Error(`Ошибка: ${response.statusText}`);
        }

        alert('Заказ оформлен успешно!');
        cart.length = 0;
        localStorage.removeItem('cart');
        event.target.reset();
        updateCartUI();
    } catch (error) {
        console.error('Ошибка:', error);
        alert('Не удалось оформить заказ. Попробуйте еще раз.');
    }
}
