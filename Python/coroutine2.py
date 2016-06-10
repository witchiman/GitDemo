import asyncio

@asyncio.coroutine
def hello():
    print('Hello world!')
    # 异步调用asyncio.sleep()
    r = yield from asyncio.sleep(10)
    print('Hello again!')

# 获取EventLoop
loop = asyncio.get_event_loop()
tasks = [hello(), hello()]
# 执行coroutine
loop.run_until_complete(asyncio.wait(tasks))
loop.close()