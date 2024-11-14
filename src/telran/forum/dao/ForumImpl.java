package telran.forum.dao;

import telran.forum.model.Post;

import java.time.LocalDate;

public class ForumImpl implements Forum {

    private Post[] posts;
    private int size;

    public ForumImpl(int capacity) {
        posts = new Post[capacity];
    }

    public boolean addPost(Post post) {
        if (size == posts.length) {
            return false;
        }
        posts[size++] = post;
        return true;
    }

//    private void increaseCapacity() {
//        Post[] tmp = new Post[posts.length * 2];
//        System.arraycopy(posts, 0, tmp, 0, posts.length);
//        posts = tmp;
//    }

    public boolean removePost(int postId) {
        int index = getIndexById(postId);
        if (index < 0) {
            return false;
        }
        System.arraycopy(posts, index + 1, posts, index, size - index - 1);
        size--;
        return true;
    }

    private int getIndexById(int postId) {
        for (int i = 0; i < size; i++) {
            if (posts[i].getPostId() == postId) {
                return i;
            }
        }
        return -1;
    }

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
        return index < 0 ? null : posts[index];
    }

    public Post[] getPostByAuthor(String author) {
        return getPostsByAuthor(author, null, null);
    }

    public Post[] getPostsByAuthor(String author, LocalDate dateFrom, LocalDate dateTo) {
        Post[] res = new Post[size];
        int resSize = 0;
        for (int i = 0; i < size; i++) {
            Post post = posts[i];
            if (post.getAuthor().equals(author) && isDateInRange(post.getDate(), dateFrom, dateTo)) {
                res[resSize++] = post;
            }
        }
        Post[] result = new Post[resSize];
        System.arraycopy(res, 0, result, 0, resSize);
        return result;
    }

    int size() {
        return size;
    }
}