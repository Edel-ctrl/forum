package telran.forum.dao;

import telran.forum.model.Post;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;

public class ForumImpl implements Forum {

    private Post[] posts;
    private int size;
    private Comparator<Post> comparator = (p1, p2) -> {
        int res = p1.getAuthor().compareTo(p2.getAuthor());
        return res != 0 ? res : Integer.compare(p1.getPostId(), p2.getPostId());
    };


    public ForumImpl() {
        //     posts = Arrays.copyOf(posts, posts.length + 1);    //WRONG WAY! posts is null here
        posts = new Post[10];// Инициализию массив с начальной ёмкостью 10 , чтобы избежать NullPointerException при обращении к posts.
    }

    public boolean addPost(Post post) {
        if (post == null || getPostById(post.getPostId()) != null) {
            return false;
        }
        //increaseCapacity()
        int index = Arrays.binarySearch(posts, 0, size, post, comparator); // принимает массив, индекс начала, длину, элемент, компаратор сравнения постов по id
        index = index >= 0 ? index : -index - 1; // если индекс найден, то возвращаем его, иначе возвращаем -index - 1
        System.arraycopy(posts, index, posts, index + 1, size - index); // копируем массив, начиная с индекса index, в массив, начиная с индекса index + 1, длиной size - index
        posts[index] = post;
        size++;
        return true;
    }
//    private void increaseCapacity() {
//        if (size >= posts.length) {
//            posts = Arrays.copyOf(posts, posts.length * 2);
//        }
//    }

    public boolean removePost(int postId) {//  метод  удаляет пост по postId и сдвигает элементы массива.
        int index = getIndexById(postId);
        if (index < 0 || size == 0) {
            return false;
        }
        System.arraycopy(posts, index + 1, posts, index, size - 1 - index); // копируем массив, начиная с индекса index + 1, в массив, начиная с индекса index, длиной size - index - 1
        posts[--size] = null; // уменьшаем размер массива на 1 и ставим null в последнюю ячейку
        return true;
    }

    //=======================================================================
    private int getIndexById(int postId) {
        for (int i = 0; i < size; i++) {
            if (posts[i].getPostId() == postId) {
                return i;
            }
        }
        return -1;
    }

    //=======================================================================
    public boolean updatePost(int postId, String content) {
        int index = getIndexById(postId);
        if (index < 0) {
            return false;
        }
        posts[index].setContent(content);
        return true;
    }

    public Post getPostById(int postId) {
        int index = getIndexById(postId);
        return index < 0 ? null : posts[index]; //
    }

    @Override
    public Post[] getPostsByAuthor(String author) {
        return getPostsByAuthor(author, null, null);
    }


//    @Override
//   public Post[] getPostsByAuthor(String author, LocalDate dateFrom, LocalDate dateTo) {
//        Post[] res = new Post[size];
//        int resSize = 0;
//        for (int i = 0; i < size; i++) {
//            Post post = posts[i];
//            if (post.getAuthor().equals(author) && isDateInRange(post.getDate().toLocalDate(), dateFrom, dateTo)) {
//                res[resSize++] = post;
//            }
//        }
////        Post[] result = new Post[resSize];
////        System.arraycopy(res, 0, result, 0, resSize);
////        return result;
//        return Arrays.copyOf(res, resSize);
//    }
    //--------------------------------------------------------------

    public Post[] getPostsByAuthor(String author, LocalDate dateFrom, LocalDate dateTo) {
        if (author == null) {
            return new Post[0];
        }
        // Используем бинарный поиск для нахождения любого индекса поста с заданным автором
        Post dummyPost = new Post(Integer.MIN_VALUE, "", author, "");
        int index = Arrays.binarySearch(posts, 0, size, dummyPost, comparator);
        if (index < 0) {
            index = -index - 1;
        } else {
            // Пройти назад, чтобы найти первый индекс с данным автором
            while (index > 0 && posts[index - 1].getAuthor().equals(author)) {
                index--;
            }
        }
        int startIndex = index;
        // Пройти вперёд, чтобы найти последний индекс с данным автором
        while (index < size && posts[index].getAuthor().equals(author)) {
            index++;
        }
        int endIndex = index;

        // Создаём временный массив для хранения результатов
        Post[] tempPosts = new Post[endIndex - startIndex];
        int count = 0;
        for (int i = startIndex; i < endIndex; i++) {
            if (isDateInRange(posts[i].getDate().toLocalDate(), dateFrom, dateTo)) {
                tempPosts[count++] = posts[i];
            }
        }
        // Копируем результаты в массив нужного размера
        return Arrays.copyOf(tempPosts, count);
    }

    //-------------------------------------------------------------------


    // Получение постов по автору и диапазону дат
//    public Post[] getPostsByAuthor(String author, LocalDate dateFrom, LocalDate dateTo) {
//        if (author == null) {
//            return new Post[0];
//        }
//        // Использую бинарный поиск для нахождения начального индекса постов с заданным автором
//        Post dummyPost = new Post(0, "", author, "");
//        int startIndex = Arrays.binarySearch(posts, 0, size, dummyPost, comparator);
//        if (startIndex < 0) {
//            startIndex = -startIndex - 1;
//        }
//        // Нахожу конец диапазона постов с заданным автором
//        int endIndex = startIndex;
//        while (endIndex < size && posts[endIndex].getAuthor().equals(author)) {
//            endIndex++;
//        }
//        // Создаю временный массив для хранения результатов
//        Post[] tempPosts = new Post[endIndex - startIndex];
//        int count = 0;
//        for (int i = startIndex; i < endIndex; i++) {
//            if (isDateInRange(posts[i].getDate().toLocalDate(), dateFrom, dateTo)) {
//                tempPosts[count++] = posts[i];
//            }
//        }
//        // Копирую результаты в массив нужного размера
//        return Arrays.copyOf(tempPosts, count);
//    }
//-----------------------------------------------------------------------------------------------------------------

    //    private boolean isDateInRange(LocalDate date, LocalDate dateFrom, LocalDate dateTo) {
//        return (date.isEqual(dateFrom) || date.isAfter(dateFrom)) && (date.isEqual(dateTo) || date.isBefore(dateTo));
//    }
    private boolean isDateInRange(LocalDate date, LocalDate dateFrom, LocalDate dateTo) {
        boolean afterFrom = dateFrom == null || !date.isBefore(dateFrom); // date >= dateFrom
        boolean beforeTo = dateTo == null || date.isBefore(dateTo);       // date < dateTo
        return afterFrom && beforeTo;
    }

    public int size() {
        return size;
    }
}