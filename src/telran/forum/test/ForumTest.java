package telran.forum.test;

import org.junit.jupiter.api.*;
import telran.forum.dao.Forum;
import telran.forum.dao.ForumImpl;
import telran.forum.model.Post;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ForumTest {

    private Forum forum;
    private Post[] posts;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        forum = new ForumImpl();
        posts = new Post[5];
        now = LocalDateTime.now();

        posts[0] = new Post(1, "Title1", "Author1", "Content1");
        posts[1] = new Post(2, "Title2", "Author2", "Content2");
        posts[2] = new Post(3, "Title3", "Author1", "Content3");
        posts[3] = new Post(4, "Title4", "Author3", "Content4");
        posts[4] = new Post(5, "Title5", "Author1", "Content5");

        // Устанавливаю даты для постов
        posts[0].setDate(now.minusDays(5));
        posts[1].setDate(now.minusDays(4));
        posts[2].setDate(now.minusDays(3));
        posts[3].setDate(now.minusDays(2));
        posts[4].setDate(now.minusDays(1));

        for (int i = 0; i < posts.length; i++) {
            forum.addPost(posts[i]);
        }
    }

    @Test
    void testAddPost() {
        assertFalse(forum.addPost(null));
        assertFalse(forum.addPost(posts[2])); // Пост уже существует
        Post newPost = new Post(6, "Title6", "Author6", "Content6");
        newPost.setDate(now); // Устанавливаем дату для нового поста
        assertTrue(forum.addPost(newPost));
        assertEquals(6, forum.size());
        assertFalse(forum.addPost(newPost)); // Попытка добавить тот же пост снова
    }

    @Test
    void testRemovePost() {
        assertFalse(forum.removePost(6)); // Пост с id=6 не существует
        assertTrue(forum.removePost(1));  // Удаляем пост с id=1
        assertEquals(4, forum.size());
    }

    @Test
    void testUpdatePost() {
        assertTrue(forum.updatePost(2, "newContent"));
        assertFalse(forum.updatePost(6, "newContent")); // Пост с id=6 не существует
        assertEquals("newContent", forum.getPostById(2).getContent());
    }

    @Test
    void testGetPostById() {
        assertNull(forum.getPostById(6)); // Пост с id=6 не существует
        assertEquals(posts[2], forum.getPostById(3));  // Пост с id=3 должен соответствовать posts[2]
    }

    @Test
    void testGetPostByAuthor() {
        Post[] expectedPosts = new Post[]{posts[0], posts[2], posts[4]};
        assertArrayEquals(expectedPosts, forum.getPostsByAuthor("Author1"));
        assertArrayEquals(new Post[]{posts[1]}, forum.getPostsByAuthor("Author2"));
        assertArrayEquals(new Post[]{}, forum.getPostsByAuthor("Author10")); // Постов с таким автором нет
    }

    @Test
    void testGetPostsByAuthorWithDates() {
        LocalDate dateFrom = now.minusDays(4).toLocalDate();
        LocalDate dateTo = now.toLocalDate();

        Post[] expected = new Post[]{posts[2], posts[4]};
        Post[] actual = forum.getPostsByAuthor("Author1", dateFrom, dateTo);

        assertArrayEquals(expected, actual);

        // Тест с датами, когда нет постов
        dateFrom = now.minusDays(10).toLocalDate();
        dateTo = now.minusDays(6).toLocalDate();

        expected = new Post[]{};
        actual = forum.getPostsByAuthor("Author1", dateFrom, dateTo);

        assertArrayEquals(expected, actual);
    }

    @Test
    void testSize() {
        assertEquals(5, forum.size()); // Проверяю, что размер форума равен 5
    }
}


//==================================================================================================================================================================================================================================


//package telran.forum.test;
//
//import org.junit.jupiter.api.*;
//import telran.forum.dao.Forum;
//import telran.forum.dao.ForumImpl;
//import telran.forum.model.Post;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.Arrays;
//
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class ForumTest {
//    private final LocalDateTime now = LocalDateTime.now();
//   // LocalDate photoDate = photos[i].getDate().toLocalDate();//
////    private final Comparator<Post> comparator = (p1, p2) -> p1.getPostId() - p2.getPostId();
//
//
//private Forum forum;
//private Post[] posts;
//
//@BeforeEach
//void setUp() {
//    forum = new ForumImpl();
//    posts = new Post[5]; // Инициализируем массив нужного размера
//
//    posts[0] = new Post(1, "Title1", "Author1", "Content1");
//    posts[1] = new Post(2, "Title2", "Author2", "Content2");
//    posts[2] = new Post(3, "Title3", "Author1", "Content3");
//    posts[3] = new Post(4, "Title4", "Author3", "Content4");
//    posts[4] = new Post(5, "Title5", "Author1", "Content5");
//    for (int i = 0; i < posts.length - 1; i++) {
//        forum.addPost(posts[i]);
//    }
//}
//    @Test
//    void testAddPost() {
//        assertFalse(forum.addPost(null));
//        assertFalse(forum.addPost(posts[2])); // Пост уже существует
//        Post newPost = new Post(6, "Title6", "Author6", "Content6");
//        assertTrue(forum.addPost(newPost));
//        assertEquals(6, forum.size()); // Теперь размер форума должен быть 6
//        assertFalse(forum.addPost(newPost)); // Попытка добавить тот же пост снова
//    }
//
//    @Test
//    void testRemovePost() {
//        assertFalse(forum.removePost(6));
//        assertTrue(forum.removePost(1));
//        assertEquals(4, forum.size());
//    }
//    @Test
//    void testUpdatePost() {
//        assertTrue(forum.updatePost(2, "newContent"));
//        assertFalse(forum.updatePost(6, "newContent"));// Пост с id=6 не существует
//        assertEquals("newContent", forum.getPostById(2).getContent());// проверяю, что контент поста с id=2 равен "newContent"
//    }
//    @Test
//    void testGetPostById() {
//        assertNull(forum.getPostById(6)); // проверяю, что поста с id=6 нет (null)
//        assertEquals(posts[2], forum.getPostById(3));  // проверяю, что пост с id=3 сщщтветствует posts[2]
//    }
//    @Test
//    void testGetPostByAuthor() {
//        assertArrayEquals(new Post[]{posts[0], posts[2], posts[4]}, forum.getPostsByAuthor("Author1"));
//        assertArrayEquals(new Post[]{posts[1]}, forum.getPostsByAuthor("Author2"));// проверяю, что пост с автором "Author2" равен posts[1]
//        assertArrayEquals(new Post[]{}, forum.getPostsByAuthor("Author10"));  // Постов с таким автором нет
//    }
//
////    @Test
////    void testGetPostsByAuthor() {
////        assertArrayEquals(new Post[]{posts[0], posts[2], posts[4]}, forum.getPostsByAuthor("Author1", now.toLocalDate().minusDays(10), now.toLocalDate())); // проверяю, что посты с автором "Author1" за период с now.minusDays(10) по now равны posts[0], posts[2], posts[4]
////        assertArrayEquals(new Post[]{posts[0], posts[2], posts[4]}, forum.getPostsByAuthor("Author1", now.toLocalDate().minusDays(10), now.toLocalDate().minusDays(1))); // проверяю, что посты с автором "Author1" за период с now.minusDays(10) по now.minusDays(1) равны posts[0], posts[2], posts[4]
////        assertArrayEquals(new Post[]{}, forum.getPostsByAuthor("Author10", now.toLocalDate().minusDays(10), now.toLocalDate().minusDays(1))); // проверяю, что постов с автором "Author10" за период с now.minusDays(10) по now.minusDays(1) нет
////    }
//@Test
//void testGetPostsByAuthorWithDates() {
//    LocalDate dateFrom = now.minusDays(4).toLocalDate();
//    LocalDate dateTo = now.minusDays(1).toLocalDate();
//
//    Post[] expected = new Post[]{posts[2], posts[4]};
//    Post[] actual = forum.getPostsByAuthor("Author1", dateFrom, dateTo);
//
//    assertArrayEquals(expected, actual);
//
//    // Тест с датами, когда нет постов
//    dateFrom = now.minusDays(10).toLocalDate();
//    dateTo = now.minusDays(6).toLocalDate();
//
//    expected = new Post[]{};
//    actual = forum.getPostsByAuthor("Author1", dateFrom, dateTo);
//
//    assertArrayEquals(expected, actual);
//}
//    @Test
//    void testSize() {
//        assertEquals(posts.length - 1, forum.size()); // проверяю, что размер форума равен 5
//    }
//}
