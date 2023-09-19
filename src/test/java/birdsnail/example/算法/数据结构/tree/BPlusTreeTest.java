package birdsnail.example.算法.数据结构.tree;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BPlusTreeTest {

    BPlusTree bPlusTree = new BPlusTree(5);

    @Test
    void delete() {
        bPlusTree.insert(2, 32.3);
        bPlusTree.insert(3, 22.3);
        bPlusTree.insert(4, 1);
        bPlusTree.insert(10, 6);
        bPlusTree.insert(15, 15);
        bPlusTree.insert(21, 21);
        bPlusTree.insert(22, 21);
        bPlusTree.insert(24, 21);
        bPlusTree.insert(26, 21);
        bPlusTree.insert(50, 21);
        bPlusTree.insert(60, 21);
        bPlusTree.insert(77, 21);
        bPlusTree.insert(100, 21);
        bPlusTree.insert(55, 21);

        bPlusTree.printTree();
        bPlusTree.insert(16, 16);
        bPlusTree.printTree();

        bPlusTree.insert(17, 16);
        bPlusTree.printTree();

        bPlusTree.insert(18, 18);
        bPlusTree.printTree();

        bPlusTree.delete(22);
        System.out.println("===============删除key：22============");
        bPlusTree.printTree();

        assertEquals(18, bPlusTree.search(18));

        bPlusTree.delete(26);
        System.out.println("===============删除key：26============");
        bPlusTree.printTree();

        bPlusTree.delete(2);
        System.out.println("===============删除key：2============");
        bPlusTree.printTree();
//
//        bPlusTree.delete(17);
//        System.out.println("===============删除key：17============");
//        bPlusTree.printTree();
    }

    @Test
    void insert() {
        bPlusTree.insert(1, 1);
        bPlusTree.insert(2, 2);
        bPlusTree.insert(3, 3);
        bPlusTree.insert(4, 4);
        bPlusTree.insert(5, 5);
        bPlusTree.insert(6, 6);

        Double value = bPlusTree.search(3);
        assertEquals(3.0, value);
    }

    @Test
    void search() {
    }
}