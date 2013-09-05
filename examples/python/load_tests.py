from multiprocessing import Pool
from client import FlurryClient, get_id
import time

p = Pool(10)

p.map_async(get_id, [('localhost', 9090, 10000)])
# p.map_async(get_id, [('localhost', 9091, 10000)] * 2)

# p.join()
p.close()
p.join()
# time.sleep(2)