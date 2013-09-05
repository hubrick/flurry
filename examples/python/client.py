from flurry import Flurry
from flurry.ttypes import *
from flurry.constants import *

from thrift import Thrift
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol

import time


class FlurryClient(object):
  """docstring for FlurryClient"""
  def __init__(self, host, port):
    super(FlurryClient, self).__init__()
    # Make socket
    transport = TSocket.TSocket('localhost', 9090)

    # Buffering is critical. Raw sockets are very slow
    self.transport = TTransport.TBufferedTransport(transport)

    # Wrap in a protocol
    protocol = TBinaryProtocol.TBinaryProtocol(self.transport)

    # Create a client to use the protocol encoder
    self.client = Flurry.Client(protocol)
    transport.open()

  def __getattr__(self, name):
    def method(*args):
      if args:
        return getattr(self.client, name)(args)
      else:
        return getattr(self.client, name)()
    return method

  def get_id(self):
    return self.client.get_id()

  def get_id_detailed(self):
    return self.client.get_id_detailed()

  def get_worker_id(self):
    return self.client.get_worker_id()

  def parse_id_into_tokens(self, id, worker_bits, sequence_bits):
    def rshift(val, n): return val>>n if val >= 0 else (val+0x100000000)>>n
    return [
      rshift(id, (worker_bits + sequence_bits - 1)),
      (id >> sequence_bits) & ((1 << worker_bits) - 1),
      id & ((1 << sequence_bits) - 1)
    ]

  def close(self):
    self.transport.close()


def get_id(params):
  host, port, times = params
  c = FlurryClient(host, port)
  start = time.time()
  for i in range(0, times):
    # id = c.get_id()
    # print("%s: %s" % (id, c.parse_id_into_tokens(id, 14, 10)))
    id = c.get_id_detailed()
    # print("DUDE: %s" % id)
  end = time.time()
  print '%d ids took %0.3f ms' % (times, (end-start)*1000.0)
  c.close()

if __name__ == '__main__':
    c = FlurryClient('localhost', 9090)
    print("WORKER ID: %d" % c.get_worker_id())
    print("ID: %d" % c.get_id())