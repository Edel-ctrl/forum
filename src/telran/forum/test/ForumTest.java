package telran.forum.test;

import org.junit.jupiter.api.BeforeEach;
import telran.forum.dao.Forum;
import telran.forum.dao.ForumImpl;
import telran.forum.model.Post;

import java.time.LocalDateTime;
import java.util.Comparator;

public class ForumTest {
    private final LocalDateTime now = LocalDateTime.now();
    private final int capacity = 5;
    private final Comparator<Post> comparator = (p1, p2) -> p1.getPostId() - p2.getPostId();


private Forum forum;
private Post[] posts;

@BeforeEach
void setUp() {
    forum = new ForumImpl(capacity);
    posts = new Post[capacity];
    posts[0] = new Post(1, "Title1", "Author1", "Content1", now.minusDays(7), 0);
    posts[1] = new Post(2, "Title2", "Author2", "Content2", now.minusDays(5), 0);
    posts[2] = new Post(3, "Title3", "Author1", "Content3", now.minusDays(3), 0);
    posts[3] = new Post(4, "Title4", "Author3", "Content4", now.minusDays(2), 0);
    posts[4] = new Post(5, "Title5", "Author1", "Content5", now.minusDays(1), 0);
    for (int i = 0; i < posts.length - 1; i++) {
        forum.addPost(posts[i]);
    }
}
