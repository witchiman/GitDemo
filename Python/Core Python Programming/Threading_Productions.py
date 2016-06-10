from time import ctime,sleep
from threading import *
from random import randint
from queue import Queue

class MyThread(Thread):
        def __init__(self, func, args,name=''):
                super(MyThread, self).__init__()
                self.name = name
                self.func = func
                self.args = args
        def run(self):
                self.res = self.func(*self.args)

def writeQ(queue):
        queue.put('ooo')
        print('producing objects for Q:',queue.qsize())
def readQ(queue):
        val = queue.get()
        print('consuming objects from Q:',queue.qsize())
def write(queue, num):
        for i in range(num):
                writeQ(queue)
                sleep(randint(1,3))

def read(queue, num):
        for i in range(num):
                readQ(queue)
                sleep(randint(2,5))

def main():
        queue = Queue(30)
        threads = []
        func = [write, read]
        num = randint(10,20)
        print('Production is beginning...')
        for i in range(2):
                t = MyThread(func[i], (queue, num), func[i].__name__)
                threads.append(t)
                print('threads size:',len(threads))
        for i in range(2):
                threads[i].start()
        for i in range(2):
                threads[i].join()
        print('All the things have been done!')

if __name__== '__main__' :
        main()