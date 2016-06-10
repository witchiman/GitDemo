def consumer():
    r = ''
    while True:
        n =  yield r
        if not n:
            return
        print('Consuming...', n)
        r = '200, OK!'

def producer(c):
    c.send(None)
    n = 0
    while n < 5:
        n += 1
        r = c.send(n)
        print('Status:',r)
        print('Producing...',n)
    c.close()

def main():
    c = consumer()
    producer(c)
    print('All the things have been done!')

if __name__ == "__main__":
    main()