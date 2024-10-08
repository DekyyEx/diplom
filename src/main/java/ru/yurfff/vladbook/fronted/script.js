const cart = JSON.parse(localStorage.getItem('cart')) || [];

document.addEventListener('DOMContentLoaded', () => {
    const addToCartButtons = document.querySelectorAll('.add-to-cart');
    const cartCount = document.getElementById('cart-count');
    const cartTotal = document.getElementById('cart-total');
    const cartList = document.getElementById('cart-list');

    // Обработчик кнопок "Добавить в корзину"
    addToCartButtons.forEach(button => {
        button.addEventListener('click', () => {
            const bookTitle = button.dataset.title;
            const bookPrice = parseFloat(button.dataset.price);
            addToCart(bookTitle, bookPrice);
        });
    });

    // Обработчик отправки формы заказа
    const orderForm = document.getElementById('orderForm');
    if (orderForm) {
        orderForm.addEventListener('submit', handleOrderSubmit);
    }

    // Обновляем интерфейс корзины при загрузке страницы
    updateCartUI(cartCount, cartTotal, cartList);
});

// Функция для добавления книги в корзину
function addToCart(title, price) {
    const existingItem = cart.find(item => item.title === title);
    if (existingItem) {
        existingItem.quantity += 1;  // Увеличиваем количество
    } else {
        cart.push({ title, price, quantity: 1 });  // Добавляем новую книгу
    }

    // Сохраняем корзину в локальное хранилище
    localStorage.setItem('cart', JSON.stringify(cart));
    alert(`${title} добавлена в корзину!`);
    updateCartUI();  // Обновляем UI корзины
}

// Функция для обновления интерфейса корзины
function updateCartUI(cartCount, cartTotal, cartList) {
    // Рассчитываем общее количество товаров и общую сумму
    const totalItems = cart.reduce((acc, item) => acc + item.quantity, 0);
    const totalPrice = cart.reduce((acc, item) => acc + item.price * item.quantity, 0).toFixed(2);

    // Обновляем отображение количества товаров и общей суммы
    if (cartCount) {
        cartCount.textContent = totalItems;
    }

    if (cartTotal) {
        cartTotal.textContent = `$${totalPrice}`;
    }

    if (cartList) {
        cartList.innerHTML = '';  // Очищаем список товаров в корзине
        cart.forEach(item => {
            const listItem = document.createElement('li');
            listItem.textContent = `${item.title} (x${item.quantity}) - $${(item.price * item.quantity).toFixed(2)}`;
            cartList.appendChild(listItem);
        });
    }

    // Если корзина пуста, показываем сообщение
    const emptyCartMessage = document.getElementById('empty-cart-message');
    if (cart.length === 0 && emptyCartMessage) {
        emptyCartMessage.style.display = 'block';
    } else if (emptyCartMessage) {
        emptyCartMessage.style.display = 'none';
    }
}

// Функция для отправки заказа
async function handleOrderSubmit(event) {
    event.preventDefault();  // Останавливаем обычную отправку формы

    // Получаем данные из формы
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

    // Формируем данные для отправки на сервер
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

        const responseData = await response.json();
        alert('Заказ оформлен успешно!');

        // Очистка корзины после успешного оформления
        cart.length = 0;
        localStorage.removeItem('cart');
        event.target.reset();
        updateCartUI();
    } catch (error) {
        console.error('Ошибка при оформлении заказа:', error);
        alert('Не удалось оформить заказ. Попробуйте еще раз.');
    }
}
