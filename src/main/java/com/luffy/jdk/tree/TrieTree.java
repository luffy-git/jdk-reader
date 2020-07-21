package com.luffy.jdk.tree;

import java.util.*;

/**
 * <p>
 *  单词查找树
 * </p>
 * @author luffy
 * @since 2020-07-21 13:52:01
 */
public final class TrieTree {

    /** 根节点 */
    private final Node root;

    public TrieTree(){
        // 初始化根节点
        this.root = new Node();
    }

    /**
     * <p>
     *  通过 Set 构造树
     * </p>
     * @author luffy
     * @since 2020-07-21 16:58:41
     */
    public TrieTree(Set<String> words) {
        this();
        this.addAll(words);
    }

    /**
     * <p>
     *  通过 List 构造树
     * </p>
     * @author luffy
     * @since 2020-07-21 16:58:49
     */
    public TrieTree(List<String> words){
        this();
        this.addAll(words);
    }

    /**
     * <p>
     *  通过集合对象初始化关键词树
     * </p>
     * @author luffy
     * @since 2020-07-21 16:55:50
     * @param words 关键词集合
     */
    private void addAll(Collection<String> words){
        // 校验关键词列表
        if (Objects.isNull(words) || words.isEmpty()) {
            throw new NullPointerException("words set cannot be empty");
        }
        // 初始化关键词树
        words.forEach(this::add);
    }



    /**
     * <p>
     *  添加关键词到字典树中
     * </p>
     * @author luffy
     * @since 2020-07-21 16:16:45
     * @param word 关键词
     */
    public void add(String word) {
        if (Objects.isNull(word)) {
            return;
        }

        // 首次获取从根节点开始查找
        Node pNode = this.root;
        // 拆分关键词
        char[] chars = word.toCharArray();
        // 关键词字符个数
        int len = chars.length;
        // 便利 word 关键词的所有字符,构造树结构
        for (int i = 0; i < len; i++) {

            char c = chars[i];

            /*
                如果 父亲节点 pNode 的子节点不包含 c 节点
                则初始化 key 节点为 父亲节点 pNode 节点的子节点
             */
            if (!pNode.children.containsKey(c)) {
                // 该节点的深度为当前 pNode 的深度 + 1,是否是尾节点为当前字符是否是关键词的最后一个字符
                pNode.getChildren().put(c, new Node(pNode.getDepth() + 1, i == len - 1));
            }

            /*
                如果 pNode 不包含 c 节点,则在上一步已初始化 c 节点
                继续遍历下一个字符,并且当前节点为 c 的节点
             */
            pNode = pNode.children.get(c);
        }
    }

    /**
     * <p>
     *  从树种查找是否包含该关键词
     * </p>
     * @author luffy
     * @since 2020-07-21 16:38:11
     * @param tree 字典树
     * @param word 查找的关键词
     * @return boolean [true:存在,false:不存在]
     */
    public static boolean search(TrieTree tree, String word) {
        Node pNode;
        if (Objects.isNull(word) || Objects.isNull(pNode = tree.root)) {
            return false;
        }

        // 关键词字符数组
        char[] chars = word.toCharArray();
        for (char c : chars) {
            // 如果父节点中不包含关键词则返回不存在
            if (!pNode.getChildren().containsKey(c)) {
                return false;
            } else {
                // 如果包含,则继续向树的纵深查找
                pNode = pNode.getChildren().get(c);
            }
        }

        return pNode.isTail();
    }

    /**
     * <p>
     *  查找一个关键词在树种的深度
     * </p>
     * @author luffy
     * @since 2020-07-21 16:39:33
     * @param tree 字典树
     * @param word 查找的关键词
     * @return int 关键词在树中的深度值
     */
    public static int treeDepth(TrieTree tree, String word) {
        Node pNode;
        if (Objects.isNull(word) || Objects.isNull(pNode = tree.root)) {
            return -1;
        }

        // 关键词字符数组
        char[] chars = word.toCharArray();
        for (char c : chars) {
            // 如果 pNode 中不包含起止字符,则直接返回 -1
            if (!pNode.getChildren().containsKey(c)) {
                return -1;
            } else {
                // 如果包含,则继续向树的纵深查找
                pNode = pNode.getChildren().get(c);
            }
        }

        // 返回 word 在树中的深度值
        return pNode.getDepth();
    }


    /**
     * <p>
     *  私有内部类,节点信息
     *  包含当前节点深度、是否是尾节点、以及子节点信息
     * </p>
     * @author luffy
     * @since 2020-07-21 16:09:07
     */
    private static final class Node {
        /** 节点深度 */
        private final int depth;
        /** 是否是尾节点 */
        private final boolean isTail;
        /** 当前节点的子节点 */
        private final Map<Character, Node> children;

        public Node(){
            this.depth = 0;
            this.isTail = false;
            this.children = new HashMap<>();
        }

        public Node(int depth, boolean isTail) {
            this.depth = depth;
            this.isTail = isTail;
            this.children = new HashMap<>();
        }

        public int getDepth() {
            return depth;
        }

        public boolean isTail() {
            return isTail;
        }

        public Map<Character, Node> getChildren() {
            return children;
        }
    }
}
