package com.demo.leecode.linkedlist;

class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
}

public class S021MergeTwoLists {

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode prehead = new ListNode(-1);
        ListNode prev = prehead;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                prev.next = l1;
                l1 = l1.next;
            } else {
                prev.next = l2;
                l2 = l2.next;
            }
            prev = prev.next;
        }
        prev.next = (l1 != null) ? l1 : l2;
        return prehead.next;
    }

    public static void main(String[] args) {
        S021MergeTwoLists ins = new S021MergeTwoLists();
        // 输入：1->2->4, 1->3->4
        // 输出：1->1->2->3->4->4

        ListNode l1 = new ListNode(1);
        l1.next = new ListNode(2);
        l1.next.next = new ListNode(4);

        ListNode l2 = new ListNode(1);
        l2.next = new ListNode(3);
        l2.next.next = new ListNode(4);

        ListNode l3 = ins.mergeTwoLists(l1, l2);
        ListNode temp = l3;
        while (temp != null) {
            System.out.printf(temp.val + ", ");
            temp = temp.next;
        }
        System.out.println("\nl3=" + l3); // 如果不修改l3指向的内容，那么l3的地址永远不变。
    }

}
