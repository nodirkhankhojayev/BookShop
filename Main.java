import java.util.Arrays;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Book book1 = new Book("C++", 20000);
        Book book2 = new Book("Java", 25000);
        Book book3 = new Book("Kotlin", 25000);

        Bookshop shop=new Bookshop("Books planet",1000000);

        shop.buyBooks(book1, 10);
        shop.buyBooks(book2, 10);
        shop.buyBooks(book3, 10);

        System.out.println();
        System.out.println("balance: " + shop.getBalance());
        System.out.println();
        System.out.println("selled books from book1: " + shop.sell(book1,4));
        System.out.println("selled books from book2: " + shop.sell(book2, 10));
        System.out.println("selled books from book3: " + shop.sell(book3, 3));

        shop.hasBook();

        System.out.println();
        System.out.println("this book has: " + shop.hasBook(book1));
        System.out.println("this book has: " + shop.hasBook(book2));
        System.out.println("this book has: " + shop.hasBook(book3));

        System.out.println();
        System.out.println("balance: " + shop.getBalance());


        System.out.println();
        System.out.println("books count: " + shop.getBookCount(book1));
        System.out.println("books count: " + shop.getBookCount(book2));
        System.out.println("books count: " + shop.getBookCount(book3));






    }
}

class Book{
    private String authorName;
    private int cost;

    Book(String authorName, int cost){
        this.authorName = authorName;
        this.cost = cost;
    }

    public String getAuthorName(){
        return authorName;
    }

    public int getCost(){
        return cost;
    }
}

class Bookshop{
    private int balance;
    private String name;
    private Book[] books;
    private int[] amounts;
    private int size = 10;
    private int index = 0;

    Bookshop(String name, int balance){
        this.balance = balance;
        this.name = name;

        this.books = new Book[size];
        this.amounts = new int[size];
    }

    int buyBooks(Book book, int count){
        if(book.getCost() > this.balance) return 0;
        int bookIndex = indexOfBookInBookshop(book);
        if(bookIndex == -1){
            books[index] = book;
            amounts[index] = 0;
            bookIndex = index;
            checkMassToLimit();
            index++;
        }
        if(book.getCost() * count <= balance){
            amounts[bookIndex] += count;
            this.balance -=  book.getCost()*count;
            return count;
        }
        int buyBookCount = this.balance / book.getCost();
        amounts[bookIndex] += buyBookCount;
        this.balance -= book.getCost()*buyBookCount;
        return buyBookCount;
    }

    boolean hasBook(){
        boolean result = false;
        for (int i = 0; i < index; i++){
            result |= amounts[i] >0;
        }
        return result;
    }

   boolean hasBook(Book book){
        int bookindex = indexOfBookInBookshop(book);
        if(bookindex == -1)  return false;
        return amounts[bookindex]  > 0;
   }

   public int getBalance(){
        return balance;
   }

   public int sell(Book book, int count){
        int bookIndex = indexOfBookInBookshop(book);
        if(bookIndex == -1) return 0;
        if(amounts[bookIndex] >= count){
            amounts[bookIndex] -= count;
            balance += book.getCost()*count;
            return count;
        }
        int sellBookCount = amounts[bookIndex];
        amounts[bookIndex] = 0;
        balance += book.getCost()*sellBookCount;
        return sellBookCount;
   }

   int getBookCount(Book book){
        int bookIndex = indexOfBookInBookshop(book);
        if(amounts[bookIndex] == -1) return 0;
        return amounts[bookIndex];
   }

    private  void checkMassToLimit(){
        if(index == books.length){
            size += size >>1;

            this.books = Arrays.copyOf(books,size);
            this.amounts = Arrays.copyOf(amounts, size);
        }
    }

    private int indexOfBookInBookshop(Book book){
        for (int i = 0; i < index; i++){
            if(books[i].getCost() == book.getCost() && books[i].getAuthorName() == book.getAuthorName()){
                return i;
            }
        }
        return -1;
    }
}