//
// Created by hui on 7/18/17.
//
#include <iostream>
#include <queue>
#include <cstring>
#include <stack>
#include <set>
#include <list>

using namespace std;

struct BtNode{
    int data;
    BtNode *left;
    BtNode *right;
    BtNode *parent;
};

struct ListNode {
    int data;
    ListNode *next;
};

bool findMatrix(int *matrix, int rows, int columns, int key)
{
    bool found = false;
    if(matrix!=NULL && rows>0 && columns>0)
    {
        int row = 0;
        int column = columns-1;
        while (row<=rows && column>=0)
        {
            if (matrix[row*columns + column] == key)
            {
                found = true;
                break;
            } else if ( matrix[row*columns + column] > key){
                column--;
            } else {
                row++;
            }
        }
    }
    return found;
}

void replaceBlack(char string[], int length)
{
    if (string == NULL || length<=0)
    {
        return;
    }

    int originLength = 0;
    int blackNumber = 0;
    while (string[originLength] != '\n')
    {
        originLength++;
        if (string[originLength] == ' ')
        {
            blackNumber++;
        }
    }

    int newLength = originLength + 2*blackNumber;
    int indexOfOrigin = originLength-1;
    int indexOfNew = newLength - 1;
    while (indexOfOrigin>=0 && indexOfNew>indexOfOrigin)
    {
        if (string[indexOfOrigin] != ' ')
        {
            string[indexOfNew--] = string[indexOfOrigin];
        } else {
            string[indexOfNew--] = '0';
            string[indexOfNew--] = '2';
            string[indexOfNew--] = '%';
        }
        indexOfOrigin--;
    }
}

int min(int *arr, int length)
{
    if (arr == NULL || length<=0)
    {
        return -1;
    }

    int index1 = 0;
    int index2 = length - 1;
    int indexMin = index1;   //
    while (arr[index1] >= arr[index2])
    {
        if (index2 - index1 == 1)
        {
            indexMin = index2;
            break;
        }
        indexMin = (index1 + index2) / 2;
        if (arr[index1] <= arr[indexMin])
        {
            index1 = indexMin;
        } else {
            index2 = indexMin;
        }

    }

    return arr[indexMin];
}

int numberOfOne(int number)
{
    int count = 0;
    while(number)
    {
        if (number & 1) {
            count++;
        }
        number = number>>1;
    }
    return count;
}



/*打印n位数以内的全排列即从1到999...9（n个9）内所有的数*/

//打印一个字符串，如果前面有零则跳过
void Print(char *number)
{
    bool isBeginWithZero = true;
    int length = strlen(number);
    for (int i = 0; i < length; ++i)
    {
        if (isBeginWithZero && number[i] != '0')
        {
            isBeginWithZero = false;
        }
        if (!isBeginWithZero)
        {
           cout<<number[i];
        }
    }
    cout<<" ";

}


void printPermutation(char *number,int length, int index)
{
    if (index == length - 1)
    {
        Print(number);
        return;
    }
    for (int i = 0; i < 10; ++i) {
        number[index+1] = i + '0';
        printPermutation(number, length, index+1);
    }
    cout<<endl;
}

void PermutationNumber(int n)
{
    if (n <= 0)
        return;

    char *number = new char[n+1];
    number[n] = '\0';
    for (int i = 0; i <10; ++i) {
        number[0] = i + '0';
        printPermutation(number, n, 0);
    }
    delete []number;
}

/*反转链表*/
ListNode* reverseList(ListNode *list)
{
    ListNode* reverseList = NULL;
    ListNode* p = list;
    ListNode* pre = NULL;
    while (p != NULL)
    {
        ListNode *next = p->next;

        if (next != NULL)
            reverseList = p;

        p->next = pre;
        pre = p;
        p = next;
    }

    return reverseList;
}

/*合并两个链表*/
ListNode* mergeList(ListNode* listA, ListNode* listB)
{
    if (listA == NULL)
        return listB;
    else if (listB == NULL)
        return listA;

    ListNode* listMerge = NULL;
    if (listA->data <= listB->data)
    {
        listMerge = listA;
        listMerge->next = mergeList(listA->next, listB);
    } else {
        listMerge = listB;
        listMerge->next = mergeList(listA, listB->next);
    }
}

/*判断一个棵树是不是另一棵树的子树*/
bool isSame(BtNode *tree, BtNode *subTree)//以某节点为根结点的子树是否与另一棵树拥有相同的结构
{
    if (subTree == NULL)
        return true;
    if (tree == NULL)
        return false;

    if (tree->data != subTree->data)
        return false;

    return isSame(tree->left, subTree->left) && isSame(tree->right, subTree->right);
}

bool hasSameStruct(BtNode *root, BtNode *subTree)// 判断一棵树是不是另一棵树的子树
{
    bool result = false;
    if (root != NULL && subTree != NULL)
    {
        if (root->data == subTree->data)
            result = isSame(root, subTree);
        if (!result)
            result = hasSameStruct(root->left, subTree);
        if (!result)
            result = hasSameStruct(root->right, subTree);
    }

    return result;
}


/*从里到外顺时针打印矩阵*/
void printInCircle(int** matrix, int rows, int columns, int start)   //顺时针打印矩阵
{
    int endX = columns - start - 1;
    int endY = rows - start - 1;

    for (int i = start; i <= endX ; ++i) {    //从左到右打印
        cout << matrix[start][i];
    }

    if (start < endY)                    //从上到下打印
    {
        for (int i = start+1; i < endY ; ++i) {
            cout << matrix[i][endX];
        }
    }

    if (start < endX && start < endY)    //从右到左打印
    {
        for (int i = endX-1; i >=start ; --i) {
            cout << matrix[endY][i];
        }
    }

    if (start < endX && start < endY-1)    //从下向上打印
    {
        for (int i = endY-1; i >=start+1 ; --i) {
            cout << matrix[i][start];
        }
    }
}

void printMatric(int **matrix, int rows, int columns)//从里到外打印
{
    if (matrix == NULL || rows <=0 || columns <=0)
        return;

    int start = 0;
    while (rows > 2*start && columns > 2*start)
    {
        printInCircle(matrix, rows, columns, start);
        ++start;
    }
}

/*给定一个栈的入栈序列判断一个序列是不是该栈的出栈序列*/
bool isPopOrder(int* pushOrder, int* popOrder, int length)
{
    bool result = false;

    if (pushOrder==NULL || popOrder==NULL || length<=0) {
        int *nextPush = pushOrder;
        int *nextPop = popOrder;
        stack<int> stackData;

        while (nextPop - popOrder < length) {
            while (stackData.empty() || stackData.top() != *nextPop)
            {
                if (nextPush - pushOrder == length)
                    break;
                stackData.push(*nextPush);
                ++nextPush;
            }

            if (stackData.top() != *nextPop)
                break;

            stackData.pop();
            ++nextPop;
        }

        if (stackData.empty() && nextPop - popOrder == length)
            result = true;
    }

    return result;
}

/*判断一个序列是不是二叉搜索树的后序遍历*/
bool isPostOrder(int sequence[], int length)
{
    if(sequence==NULL || length<=0)
        return false;
    int root = sequence[length-1];

    int i; //查找左子树的数，左子树的值都小于根节点
    for (i = 0; i < length - 1; ++i) {
        if (sequence[i] > root)
            break;
    }

    int j;//查找右子树的数，右子树节点的值都大于根节点
    for (j = i; j < length-1; ++j) {
        if (sequence[j] < root)
            return false;
    }

    bool left = true;  //判断左子树是不是二叉搜索树
    if (i > 0)
        left = isPostOrder(sequence, i);

    bool right = true; //判断右子树是不是二叉搜索树
    if (i < length-1)
        right = isPostOrder(sequence + i, length - i - 1);

    return left && right;
}

/*查找二叉树中某条等于指定值的路径*/
void findPath(BtNode* root, vector<int> &path, int expectNum, int&currentNum)
{
    currentNum += root->data;
    if (expectNum==currentNum && root->left==NULL && root->right==NULL) //如果root为叶子节点，路径的值即为所求
    {
        cout<< "Find a path" <<endl;
        vector<int>::const_iterator iter = path.begin();
        for (; iter != path.end() ; ++iter) {
            cout<<*iter;
        }
        cout<<endl;
    }

    if (root->left != NULL)  //左子树不为空，遍历左子树
        findPath(root->left, path, expectNum, currentNum);
    else if (root->right != NULL)
        findPath(root->right, path, expectNum, currentNum);

    currentNum-=root->data;  //返回父节点，减去当前节点的值,并在路径中删除此节点
    path.pop_back();
}

void testFindPath(BtNode *root, int expectNum)
{
    if (root == NULL)
        return;

    int currentNum = 0;
    vector<int> path;
    findPath(root, path, expectNum, currentNum);
}

/*复制一个复杂链表*/
struct CListNode {
    int data;
    CListNode *next;
    CListNode *another;
};

void insertIntoOrign(CListNode* oriList)  //第一步先在原来的链表里，依次复制每个节点，并在每个原节点后插入新节点。A->A'->B->B'
{
    CListNode* p = oriList;
    while (p != NULL)
    {
        CListNode* node = new CListNode();
        node->data = p->data;
        node->another = NULL;
        node->next = p->next;

        p->next = node;
        p = node->next;
    }
}
void dealAnother(CListNode* oriList)  //第二步，开始处理another指向的节点
{
    CListNode* p = oriList;
    while (p != NULL)
    {
        CListNode* pClone = p->next;
        if (p->another != NULL)
        {
            pClone->another = p->another->next;
        }
        p = pClone->next;
    }
}
CListNode* splitList(CListNode* oriList)//第三步，把处理后的的链表一分为二，奇数节点拼接起来为原链表，偶数节点拼接起来为复制后的链表
{
    CListNode* p = oriList;
    CListNode* newList = NULL;
    CListNode* pClone = NULL;

    if (p != NULL)
    {
        newList =  pClone = p->next;
        p->next = pClone->next;
        p = p->next;
    }

    while(p != NULL)
    {
        pClone->next = p->next;
        pClone = pClone->next;
        p->next = pClone->next;
        p = p->next;
    }

    return newList;
}

CListNode* cloneCListNode(CListNode* oriList) //三步合起来就是复制一个复杂链表的完整过程
{
    insertIntoOrign(oriList);
    dealAnother(oriList);
    return splitList(oriList);
}

/*求一个字符串的全排列*/
void PermutationString(char* string, char* beginStr)
{
    if (*beginStr == '\0')
        cout<<string<<" ";
    else {
        for (char *ch=beginStr; *ch!='\0'; ch++)  //第一步，字符串分为两部分，第一部分是第一个字符，第二部分是剩下的字符串的，求剩下的字符的全排列
        {                                        //第二步，拿第一个字符和它后面的字符逐个交换
            char temp = *ch;
            *ch = *beginStr;
            *beginStr = temp;

            PermutationString(string, beginStr+1);

            temp = *ch;
            *ch = *beginStr;
            *beginStr = temp;
        }
    }

}
void permutationString(char *string)
{
    if (string == NULL)
        return;
    PermutationString(string, string);
}

/*检测一个数组中是否含有出现次数超过数组长度一半的数字*/

bool checkMoreThanHalf(int* arr, int length, int number) //检测出现的数是否有效
{
    int times = 0;
    for (int i = 0; i < length; ++i) {
        if (arr[i] == number)
        {
            times++;
        }
    }

    if (2*times <= length)
        return false;

    return true;
}

int moreThanHalf(int* arr, int length)
{
    if (arr == NULL || length<=0)
        return 0;

    int times = 0;
    int result = arr[0];

    for (int i = 1; i < length-1; ++i) {
        if (times == 0)
        {
            result = arr[i];
            times = 1;
        } else if (result == arr[i])
            times++;
        else
            times--;
    }

    if (!checkMoreThanHalf(arr, length, result))
        result = 0;

    return result;
}

/*查找一个数组内最小的k个数字*/
multiset<int, greater<int >> leastNumber;
void findLeastNumbers(const vector<int>& number, multiset<int, greater<int >>&leastNumber, int k)
{
    if (k<=0 || number.size()<k )
        return;

    for (vector<int >::const_iterator iter=number.begin();  iter!=number.end(); ++iter) {
        if (leastNumber.size() < k)
            leastNumber.insert(*iter);

        multiset<int, greater<int >>::const_iterator setIter = leastNumber.begin();
        if (*iter < *setIter)
        {
            leastNumber.erase(setIter);
            leastNumber.insert(*iter);
        }
    }

}

/*返回一个数组的最大子数组之和，这个数组内可能包含正数和负数*/
bool invalidInput = false;
int maxSumInArray(int *arr, int length)
{
    if (arr == NULL || length <= 0) {
        invalidInput = true;
        return 0;
    }

    int curSum = 0;
    int maxSum = 0x80000000;
    for (int i = 0; i < length; ++i) {
        if (curSum <= 0)
            curSum = arr[i];
        else
            curSum += arr[i];

        if (curSum >= maxSum)
            maxSum = curSum;
    }
    return maxSum;
}

/*输入一个整数n,求1～n内所有整数中包含1的个数*/
int PowerBase10(int n)   //返回比如10000~19999内1的个数
{
    int result = 1;
    for (unsigned int i = 0; i < n; ++i) {
        result *= 10;
    }
    return result;
}

int findNumberOfOneStr(const char* strN)  //换算成字符串处理
{
    if(!strN || *strN < '0' || *strN > '9' || *strN == '\0')
        return 0;

    int first = *strN - '0';
    unsigned int length = static_cast<unsigned int >(strlen(strN));

    if (length == 1 && first ==0)
        return 0;

    if (length == 1 && first > 0)
        return 1;

    int numFirst = 0;   //假设strN是21345
    if (first > 1)
        numFirst = PowerBase10(length-1);   //firstDigitalNumber是10000～19999中数中1的个数
    else if (first == 1)
        numFirst = atoi(strN+1) + 1;

    int numOther = first*(length-1)*PowerBase10(length-2);   //把strN分成1～1345，1346～11345,11346～21345三部分处理，这里处理后两部分

    int numRecursive = findNumberOfOneStr(strN+1);    //处理1~1345

    return numFirst + numOther + numRecursive;
}

int findNumberOfOne(int n)
{
    if (n <= 0)
        return 0;
    char strN[50];
    sprintf(strN, "%d", n);
    return  findNumberOfOneStr(strN);
}

/*求一个数组排成的最小的数，如{3,23,100}排成的最小的数是100233*/
const int MAX_NUMBER_LENGTH = 10;
char* strCompare1 = new char[2*MAX_NUMBER_LENGTH +1];
char* strCompare2 = new char[2*MAX_NUMBER_LENGTH +1];

int compare(const void* strNum1, const void* strNum2) //比较[strNum1,strNum2与[strNum2,strNum1]的大小
{
    strcpy(strCompare1, *(const char**)strNum1);
    strcat(strCompare1, *(const char**)strNum2);

    strcpy(strCompare2, *(const char**)strNum2);
    strcat(strCompare2, *(const char**)strNum1);

    return strcmp(strCompare1, strCompare2);
}

void minNumberInArray(int *numbers, int length)
{
    if (numbers == NULL || length <=0)
        return;

    char** strNumbers = (char**)new int[length];

    for (int i=0; i<length; ++i)
    {
        strNumbers[i] = new char[MAX_NUMBER_LENGTH + 1];
        sprintf(strNumbers[i], "%d", numbers[i]);
    }

    qsort(strNumbers, length, sizeof(char*), compare);  //调用库函数进行排序,时间复杂度为O(nlogn)

    for (int i = 0; i < length; ++i) {
        cout<<strNumbers[i];
    }
    cout<<endl;

    for (int i = 0; i < length; ++i) {
        delete[] strNumbers[i];
    }

    delete[] strNumbers;
}

/*获取第n个丑数,丑数即因子只有2,3,5的数*/
bool isUglyNumber(int number)
{
    while (number % 2 == 0 )
        number /= 2;
    while(number %  3 == 0)
        number /= 3;
    while (number % 5 == 0)
        number /= 5;

    return (number == 1) ? true : false;
}

int getUglyNumber1(int n)  //第一种方法，获取第n个丑数。缺陷，即使一个数不是丑数也要对它进行计算，时间复杂度比较高
{
    if(n <= 0)
        return 0;
    int count = 0;
    int number = 0;

    while (count < n)
    {
        number++;
        if (isUglyNumber(number))
            count++;
    }
    return number;
}

int minNumber(int number1, int number2, int number3)
{
    int min = (number1 <= number2) ? number1 : number2;
    return (min <= number3) ? min : number3;
}

int getUglyNumber2(int n)   //求第n个丑数，以空间时换时间
{
    if (n <= 0)
        return 0;

    int* uglyNumbers = new int[n];
    uglyNumbers[0] = 1;
    int nextUglyNum = 1;

    int* numbers2 = uglyNumbers;
    int* numbers3 = uglyNumbers;
    int* numbers5 = uglyNumbers;

    while (nextUglyNum < n)
    {
        int min = minNumber(*numbers2*2, *numbers3*3, *numbers5*5);
        uglyNumbers[nextUglyNum] = min;

        while (*numbers2 * 2 <= min)
            ++numbers2;

        while (*numbers3 * 3 <= min)
            ++numbers3;

        while (*numbers5 * 5 <= min)
            ++numbers5;

        ++nextUglyNum;
    }

    int ugly = uglyNumbers[nextUglyNum-1];
    delete[] uglyNumbers;
    return ugly;
}

/*求一个字符串里第一个只出现一次的字母*/
char findFirstOnlyChar(char* str)
{
    if (str == NULL)
        return '\0';
    int* nums = new int[256];
    for (int i = 0; i < 256; ++i) {  //初始化
        nums[i] = 0;
    }

    char *p = str;
    while(*p)       //遇到一个字符对应的nums加1
    {
        nums[*(str++)]++;
    }

    p = str;
    while (p)            //重新扫描,找出第一个值为1的字符
    {
        if (nums[*p] == 1)
            return *p;
        p++;
    }
}

/*求逆序数*/
int inversePairsCore(int* data, int* copy, int start, int end)
{
    if (start == end)
    {
        copy[start] = data[start];
        return 0;
    }

    int length = (end - start) / 2;

    int left = inversePairsCore(copy, data, start, start + length);  //递归统计左边的逆序数
    int right = inversePairsCore(copy, data, start + length + 1, end); //递归统计右边的逆序数

    int i = start + length;  //i为前半段最后一个数字的下标
    int j = end;            //j为后半段最后一个数字的下标
    int indexCopy = end;
    int count = 0;

    while (i >= start && j >= start + length + 1)
    {
        if (data[i] > data[j])
        {
            copy[indexCopy--] = data[i--];
            count += j - start - length;
        } else {
            copy[indexCopy--] = data[j--];
        }
    }

    while (i >= start)
        copy[indexCopy--] = data[i--];

    while (j >= start  + length + 1)
        copy[indexCopy--] = data[j--];

    return left + right + count;

}

int inversePairs(int* data, int length)
{
    if (data == NULL || length <= 0)
        return 0;

    int* copy = new int[length];

    for (int i = 0; i < length; ++i) {
        copy[i] = data[i];
    }

    int count = inversePairsCore(data, copy, 0, length-1);
    delete[] copy;
    return count;
}

/*求两个链表的第一个公共结点*/

int getListLength(ListNode *list)
{
    int length = 0;
    ListNode* node = list;
    while (list != NULL)
    {
        length++;
        node = node->next;
    }
    return length;
}

ListNode* getCommonListNode(ListNode* listA, ListNode* listB)
{
    int lengthA = getListLength(listA);
    int lengthB = getListLength(listB);

    int diff = lengthA - lengthB;
    ListNode* pLong = listA;
    ListNode* pShort = listB;

    if (diff < 0)
    {
        diff = lengthB - lengthA;
        pLong = listB;
        pShort = listA;
    }

    for (int i = 0; i < diff; ++i) {  //先移动长链表的指针
        pLong = pLong->next;
    }

    while (pLong!=NULL && pShort!=NULL && pLong->data!=pShort->data)  //同时遍历两个链表，比较查找第一个公共节点
    {
        pLong = pLong->next;
        pShort = pShort->next;
    }

    ListNode* commonNode = pLong;    //得到第一个公共节点
    return commonNode;
}


/*给定一个数组，求其中某个数出现的次数*/
int getFirstKey(int *data, int length, int key, int start, int end) //求key第一次出现的位置
{
    if (start > end)
        return -1;

    int mid = (start + end) / 2;
    if (data[mid] == key)
    {
        if ((mid > 0 &&data[mid-1] != key) || mid==0)
            return mid;
        else
            end = mid -1;
    } else if (data[mid] > key)
        end = mid - 1;
    else
        start = mid + 1;

    return getFirstKey(data, length, key, start, end);
}
int getLastKey(int* data, int length, int key, int start, int end) //求key最后一次出现的位置
{
    if (start > end)
        return -1;

    int mid = (start + end) / 2;
    if (data[mid] == key)
    {
        if ((mid < length-1 && data[mid+1] != key) || mid == length-1)
            return mid;
        else
            start = mid + 1;
    } else if (data[mid] < key)
        start = mid + 1;
    else
        end = mid - 1;
    return getLastKey(data, length, key, start, end);
}
int getTimesOfKey(int* data, int length, int key)
{
    int number = 0;
    if (data != NULL || length > 0)
    {
        int first = getFirstKey(data, length, key, 0, length-1);
        int last = getLastKey(data, length, key, 0, length-1);
        number = last - first + 1;
    }
    return number;
}

/*判断一棵二叉树是不是平衡二叉树*/
int treeDepth(BtNode* root)  //获取树的高度
{
    if (root == NULL)
        return 0;
    int left = treeDepth(root->left);
    int right = treeDepth(root->right);
    return  (left > right) ? (left+1) : (right+1);
}
bool isBalancedTree(BtNode* root)
{
    if (root == NULL)
        return true;

    int heightL = treeDepth(root->left);
    int heightR = treeDepth(root->right);
    int diff = heightL - heightR;
    if (diff <1 || diff >-1)
        return false;
    return isBalancedTree(root->left) && isBalancedTree(root->right);
}
int isBalancedTree(BtNode* root, int *depth)   //改进的算法
{
    if (root == NULL)
    {
        *depth = 0;
        return false;
    }

    int left, right;
    if(isBalancedTree(root->left, &left) && isBalancedTree(root->right, &right))
    {
        int diff = left - right;
        if (diff >= -1 && diff <= 1)
        {
            *depth = 1 + (left > right) ? left : right;
            return true;
        }
    }
    return false;
}

/*一个数组里除了两个数字外都只出现了两次，求出这两个数字，要求时间复杂度为O(n)，空间复杂度为O(1)*/
bool isBitOne(int num, unsigned int n) //判断一个数的二进制从右边数起的第n位是不是1
{
    num = num>>n;
    return num & 1;
}
unsigned int findFirstBitOne(int num) //从一个数的二进制右边数查找第一个为1的位置
{
    int index = 0;
    while((num & 1) == 0 && index <= 8* sizeof(int))
    {
        num = num >> 1;
        index++;
    }
    return index;
}

void findOnceNumber(int* nums, int length, int* num1, int* num2)
{
    if (nums == NULL || length<2)
        return;

    int resultExclusiveOr = 0;
    for (int i = 0; i < length; ++i) {
        resultExclusiveOr^=nums[i];
    }

    int firstOne = findFirstBitOne(resultExclusiveOr);

    for (int i = 0; i < length; ++i) {
        if (isBitOne(nums[i], firstOne))
        {
            *num1^=nums[i];
        } else {
            *num2^=nums[i];
        }
    }
}
/*输入一个正数s，打印所有和为s的连续正数序列（至少包含2个数）*/
void printContinuousSequence(int small, int big)
{
    for (int i = small; i <= big ; ++i) {
        cout<<i<<" ";
    }
    cout<<endl;
}

void findContinuousSequence(int sum)
{
    if (sum < 3)
        return;

    int small = 1;
    int big = 2;
    int mid = (1 + sum) / 2;  //由于至少要包含两个数small不能超过mid
    int curSum = small + big;

    while (small < mid) {
        if (curSum == sum)
        {
            printContinuousSequence(small, big);
        }
        while (curSum > sum && small < mid)
        {
            curSum -= small;
            small++;

            if (curSum == sum)
                printContinuousSequence(small, big);
        }

        big++;
        curSum += big;
    }
}

/*反转单词词序*/
void reverse(char* begin, char* end)
{
    if (begin == NULL || end == NULL)
        return;
    while (begin < end)
    {
        char temp = *begin;
        *begin = *end;
        *end = temp;

        begin++;
        end--;
    }
}

void reverseString(char* string)
{
    if (string == NULL)
        return;

    char* begin = string;
    char* end = string;

    while (*end != '\0')
        end++;
    end--;       //指向最后一个字符

    reverse(begin, end);  //先反转整个字符串

    begin = end = string;

    while (*begin != '\0')   //翻转每个单词
    {
         if (*begin ==' ')
         {
             begin++;
             end++;
         } else if (*end == ' ' || *end == '\0') {
             reverse(begin, --end);
             begin = ++end;
         } else {
             end++;
         }
    }
}

/*左旋转字符串的n位*/
void leftRotateString(char* string, int n)
{
    if (string != NULL)
    {
        int length = static_cast<int >(strlen(string));
        char* firstStart = string;
        char* firstEnd = string + n-1;
        char* secondStart = string + n;
        char* secondEnd = string + length -1;
        if (length > 0 && n > 0 && n < length)
        {
            reverse(firstStart, firstEnd);  //反转前前面n位
            reverse(secondStart, secondEnd); //反转后面length-n位
            reverse(firstStart, secondEnd);  //反转整个字符串
        }
    }
}

/*随机从扑克中抽五张牌，判断这五张牌是不是顺子。用0代表大小王*/
int compare2(const void* arg1, const void* arg2)
{
    return *(int*)arg1 - *(int*)arg2;
}

bool isContinuousCard(int* cards, int length)
{
    if (cards == NULL || length<1)
        return false;

    qsort(cards, length, sizeof(int), compare2);

    int numZero = 0;  //统计大小王的数量，可以代表任意牌
    for (int i = 0; i < length; ++i) {
        if (cards[i] == 0)
            numZero++;
    }

    int numGap = 0; //间断牌的张数
    int small = numZero;
    int big = small + 1;

    while (big < length)
    {
        if (cards[small] == cards[big])  //有两张一样的牌，出现对子，不是顺子
            return false;
        numGap += cards[big] - cards[small] -1;
        small = big;
        big++;
    }

    return (numZero >= numGap) ? true : false;
}

/*0,1...n-1围成一圏，删除第m个数，求最后剩下的数*/
int remainInCircle(int n, int m)
{
    if (n<1 || m <1)
        return -1;

    list<int> nums;
    for (int i = 0; i < n; ++i) {
        nums.push_back(i);
    }

    list<int>::iterator iter = nums.begin();
    while(nums.size() > 1)
    {
        for (int j = 0; j < m-1; ++j) {
            iter++;
            if (iter == nums.end())
                iter = nums.begin();
        }

        list<int>::iterator next = ++iter;
        if (next == nums.end())
            next = nums.begin();

        iter--;
        nums.erase(iter);
        iter = next;
    }

    return (*iter);
}
int remainInCircle2(int n, int m)  //一种更加高效的算法
{
    if (n < 1 || m < 1)
        return -1;

    int last = 0;
    for (int i=2; i<=n; i++)
        last = (last + m) % i;

    return last;
}

/*不使用四则运算实现加法*/
int add(int num1, int num2)
{
    int sum, carry;
    do
    {
        sum = num1 ^ num2;
        carry = (num1 & num2) << 1;
        num1 = sum;
        num2 = carry;
    }
    while (num2 != 0);

    return num1;
}

/*把一个字符串转为整型*/
enum Status {Valid = 0, Invalid};
int status = Valid;
int StrToInt(char* str)
{
    status = Invalid;
    long long num = 0;

    if (str != NULL && *str != '\0')
    {
        bool minus = false;    //判断是否负数
        if (*str == '-')
        {
            minus = true;
            str++;
        } else if (*str == '+') {
            str++;
        }

        while(*str != '\0')
        {
            if (*str >= '0' && *str <= '9')
            {
                int flag = minus ? -1 : 1;
                num = num * 10 + flag * (*str - '0');

                if ((!minus && num > 0x7FFFFFFF) || (minus && num <(unsigned int)0x80000000)) //判断是否溢出
                {
                    num = 0;
                    break;
                }

                str++;
            } else {
                num = 0;
                break;
            }
        }
    }

    if (*str == '\0')
        status = Valid;

    return (int)num;
}