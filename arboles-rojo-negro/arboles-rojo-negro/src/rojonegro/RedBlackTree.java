package rojonegro;

import java.util.ArrayList;
import java.util.List;

public class RedBlackTree<T extends Comparable<T>> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class Node {
        T key;
        Node left, right, parent;
        boolean color;

        Node(T key, boolean color) {
            this.key = key;
            this.color = color;
        }
    }

    private final Node nil; 
    private Node root;
    private int size;

    public RedBlackTree() {
        nil = new Node(null, BLACK);
        nil.left = nil;
        nil.right = nil;
        nil.parent = nil;
        root = nil;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return root == nil;
    }

    public boolean contains(T key) {
        return find(key) != nil;
    }

    public List<T> searchPath(T key) {
        List<T> path = new ArrayList<>();
        Node current = root;
        while (current != nil) {
            path.add(current.key);
            int cmp = key.compareTo(current.key);
            if (cmp == 0) {
                break;
            }
            current = (cmp < 0) ? current.left : current.right;
        }
        return path;
    }

    private Node find(T key) {
        Node current = root;
        while (current != nil) {
            int cmp = key.compareTo(current.key);
            if (cmp == 0) {
                return current;
            }
            current = (cmp < 0) ? current.left : current.right;
        }
        return nil;
    }

    private void rotateLeft(Node x) {
        Node y = x.right;         
        x.right = y.left;          
        if (y.left != nil) {
            y.left.parent = x;
        }
        y.parent = x.parent;       
        if (x.parent == nil) {
            root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;              
        x.parent = y;
    }

    private void rotateRight(Node y) {
        Node x = y.left;     
        y.left = x.right;         
        if (x.right != nil) {
            x.right.parent = y;
        }
        x.parent = y.parent;      
        if (y.parent == nil) {
            root = x;
        } else if (y == y.parent.right) {
            y.parent.right = x;
        } else {
            y.parent.left = x;
        }
        x.right = y;             
        y.parent = x;
    }
    
    public boolean insert(T key) {
        Node parent = nil;
        Node current = root;
        while (current != nil) {
            parent = current;
            int cmp = key.compareTo(current.key);
            if (cmp == 0) {
                return false;
            }
            current = (cmp < 0) ? current.left : current.right;
        }

        Node z = new Node(key, RED);
        z.left = nil;
        z.right = nil;
        z.parent = parent;

        if (parent == nil) {
            root = z;
        } else if (key.compareTo(parent.key) < 0) {
            parent.left = z;
        } else {
            parent.right = z;
        }
        size++;

        insertFixup(z);
        return true;
    }

    private void insertFixup(Node z) {
        while (z.parent.color == RED) {
            if (z.parent == z.parent.parent.left) {
                Node uncle = z.parent.parent.right;
                if (uncle.color == RED) {
                    z.parent.color = BLACK;
                    uncle.color = BLACK;
                    z.parent.parent.color = RED;
                    z = z.parent.parent;
                } else {
                    if (z == z.parent.right) 
                        z = z.parent;
                        rotateLeft(z);
                    }
                    z.parent.color = BLACK;
                    z.parent.parent.color = RED;
                    rotateRight(z.parent.parent);
                }
            } else {
                Node uncle = z.parent.parent.left;
                if (uncle.color == RED) {
                    z.parent.color = BLACK;
                    uncle.color = BLACK;
                    z.parent.parent.color = RED;
                    z = z.parent.parent;
                } else {
                    if (z == z.parent.left) {
                        z = z.parent;
                        rotateRight(z);
                    }
                    z.parent.color = BLACK;
                    z.parent.parent.color = RED;
                    rotateLeft(z.parent.parent);
                }
            }
        }
        root.color = BLACK; 
    }

    public int height() {
        return height(root);
    }

    private int height(Node n) {
        if (n == nil) {
            return -1;
        }
        return 1 + Math.max(height(n.left), height(n.right));
    }

    public int blackHeight() {
        int bh = 0;
        Node n = root;
        while (n != nil) {
            n = n.left;
            if (n.color == BLACK) {
                bh++;
            }
        }
        return bh;
    }

    public boolean isValid() {
        if (root.color != BLACK) {
            return false;
        }
        return check(root, null, null) != -1;
    }

    private int check(Node n, T min, T max) {
        if (n == nil) {
            return 1;
        }
        if (min != null && n.key.compareTo(min) <= 0) {
            return -1; 
        }
        if (max != null && n.key.compareTo(max) >= 0) {
            return -1;
        }
        if (n.color == RED && (n.left.color == RED || n.right.color == RED)) {
            return -1; 
        }
        int left = check(n.left, min, n.key);
        int right = check(n.right, n.key, max);
        if (left == -1 || right == -1 || left != right) {
            return -1; 
        }
        return left + (n.color == BLACK ? 1 : 0);
    }

    public List<T> inOrder() {
        List<T> result = new ArrayList<>();
        inOrder(root, result);
        return result;
    }

    private void inOrder(Node n, List<T> result) {
        if (n == nil) {
            return;
        }
        inOrder(n.left, result);
        result.add(n.key);
        inOrder(n.right, result);
    }

    public void print() {
        if (root == nil) {
            System.out.println("(árbol vacío)");
            return;
        }
        print(root, 0);
    }

    private void print(Node n, int depth) {
        if (n == nil) {
            return;
        }
        print(n.right, depth + 1);
        System.out.println("    ".repeat(depth) + n.key + (n.color == RED ? "(R)" : "(N)"));
        print(n.left, depth + 1);
    }
}
