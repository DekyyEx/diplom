package ru.yurfff.vladbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yurfff.vladbook.model.OrderItem;
import ru.yurfff.vladbook.model.Book;
import ru.yurfff.vladbook.repository.OrderItemRepository;
import ru.yurfff.vladbook.repository.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private final OrderItemRepository orderItemRepository;
    private final BookRepository bookRepository;
    private final BookService bookService;

    @Autowired
    public CartService(OrderItemRepository orderItemRepository, BookRepository bookRepository, BookService bookService) {
        this.orderItemRepository = orderItemRepository;
        this.bookRepository = bookRepository;
        this.bookService = bookService;
    }

    // Получение всех товаров в корзине
    public List<OrderItem> getAllCartItems() {
        return orderItemRepository.findAll();
    }

    // Добавление книги в корзину
    public OrderItem addToCart(Long bookId, Integer quantity) {
        // Проверяем, существует ли книга с таким ID
        Optional<Book> bookOpt = bookRepository.findById(bookId);
        if (bookOpt.isEmpty()) {
            throw new IllegalArgumentException("Book not found");
        }

        Book book = bookOpt.get();

        // Проверяем, доступна ли книга
        if (!book.isAvailable()) {
            throw new IllegalArgumentException("The book " + book.getTitle() + " is not available.");
        }

        // Проверяем, есть ли уже этот товар в корзине
        List<OrderItem> existingItems = orderItemRepository.findByBook(book);
        if (!existingItems.isEmpty()) {
            // Если есть, увеличиваем количество
            OrderItem existingItem = existingItems.get(0); // Берём первую найденную
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            return orderItemRepository.save(existingItem);
        }

        // Если нет, создаём новый OrderItem
        OrderItem orderItem = new OrderItem();
        orderItem.setBook(book);
        orderItem.setQuantity(quantity);

        // Сохраняем товар в корзине
        OrderItem savedItem = orderItemRepository.save(orderItem);

        // Убираем книгу из доступных (снижаем её доступность)
        bookService.removeBookFromAvailable(bookId);

        return savedItem;
    }

    // Удаление книги из корзины
    public void removeFromCart(Long id) {
        Optional<OrderItem> orderItemOpt = orderItemRepository.findById(id);
        if (orderItemOpt.isEmpty()) {
            throw new IllegalArgumentException("Order item not found");
        }

        OrderItem orderItem = orderItemOpt.get();
        Book book = orderItem.getBook();

        // Возвращаем книгу обратно в доступные
        bookService.returnBookToAvailable(book.getId());

        // Удаляем товар из корзины
        orderItemRepository.deleteById(id);
    }

    // Очистка корзины
    public void clearCart() {
        List<OrderItem> allItems = orderItemRepository.findAll();
        for (OrderItem item : allItems) {
            Book book = item.getBook();
            // Возвращаем книгу обратно в доступные
            bookService.returnBookToAvailable(book.getId());
        }

        // Удаляем все товары из корзины
        orderItemRepository.deleteAll();
    }

    // Получение товара из корзины по ID
    public OrderItem getCartItemById(Long id) {
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order item not found"));
    }
}
