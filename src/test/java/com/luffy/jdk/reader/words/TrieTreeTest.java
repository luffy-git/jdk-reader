package com.luffy.jdk.reader.words;

import com.luffy.jdk.tree.TrieTree;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class TrieTreeTest {

    @Test
    void testTree() {
        TrieTree tree = new TrieTree();
        tree.add("中国人");
        tree.add("中国男人");
        tree.add("中国女人");
        tree.add("李");
        log.info("startWith:" + TrieTree.treeDepth(tree, "中国"));
        log.info("search:" + TrieTree.search(tree, "Luffy"));
    }

    @Test
    void testListTree() {
        TrieTree tree = new TrieTree(Arrays.asList("中国人","中国男人","中国女人","李"));
        log.info("startWith:" + TrieTree.treeDepth(tree, "中国"));
        log.info("search:" + TrieTree.search(tree, "Luffy"));
    }

    @Test
    void testSetTree() {
        Set<String> words = new HashSet<>();
        words.add("中国人");
        words.add("中国男人");
        words.add("中国女人");
        words.add("李");
        TrieTree tree = new TrieTree(words);
        log.info("startWith:" + TrieTree.treeDepth(tree, "中国"));
        log.info("search:" + TrieTree.search(tree, "Luffy"));
    }
}
