import logging
import threading
import time

def thread_function(name, t):
    logging.info("Thread %s: starting", name)
    time.sleep(t)
    logging.info("Thread %s: finishing", name)

if __name__ == "__main__":
    format = "%(asctime)s: %(message)s"
    logging.basicConfig(format=format, level=logging.INFO, datefmt="%H:%M:%S")
    logging.info("Main    : before creating threads")
    x1 = threading.Thread(target=thread_function, args=(1,4,))
    x2 = threading.Thread(target=thread_function, args=(2,2,))
    logging.info("Main    : before running threads")
    x1.start()
    x2.start()
    logging.info("Main    : wait for the threads to finish")
    x1.join()
    x2.join()
    logging.info("Main    : all done")
