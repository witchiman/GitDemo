//
// Created by hui on 7/18/17.
// 一些关于二叉树的算法
//
#include <iostream>
#include <queue>

using namespace std;

struct BtNode{
    int data;
    BtNode *left;
    BtNode *right;
    BtNode *parent;
};

/*判断是否是完全二叉树*/
bool isCompleteTree(BtNode *root)
{
     if(root == NULL) return false;
     queue<BtNode*> q;
     q.push(root);
     bool result = true;
     bool haveChildren = false;
     while(!q.empty())
     {
         BtNode *p = q.front();
         q.pop();
         if(haveChildren)
         {
             if(p->left!=NULL&&p->left!=NULL)
             {
                 result = false;
                 break;
             }
         }else{
             if(p->left!=NULL&&p->right!=NULL)
             {
                 q.push(p->left);
                 q.push(p->right);
             } else if(p->left!=NULL && p->right ==NULL) {
                 q.push(p->left);
                 haveChildren = true;
             } else if(p->left==NULL && p->right !=NULL){
                 result = false;
                 break;
             } else {
                 haveChildren = true;
             }

         }
     }

    return result;
}

/*右旋*/
BtNode* roateRight(BtNode *root, BtNode *x)
{
    BtNode *p = x->left;
    x->left = p->right;
    if(p->right != NULL)
    {
        p->right->parent = x;
    }
    p->parent = x->parent;
    if(x->parent == NULL)
    {
        root = p;
    } else {
       if(x->parent->left == x)
           x->parent->left = p;
       if(x->parent->right == x)
           x->parent->right = p;
    }

    p->right = x;
    x->parent = p;


}
