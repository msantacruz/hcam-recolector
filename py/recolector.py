#!/usr/bin/env python
'''
Pymodbus Synchronous Client Examples
--------------------------------------------------------------------------

The following is an example of how to use the synchronous modbus client
implementation from pymodbus.

It should be noted that the client can also be used with
the guard construct that is available in python 2.5 and up::

    with ModbusClient('127.0.0.1') as client:
        result = client.read_coils(1,10)
        print result
'''
#---------------------------------------------------------------------------# 
# import the various server implementations
#---------------------------------------------------------------------------# 
#from pymodbus.client.sync import ModbusTcpClient as ModbusClient
#from pymodbus.client.sync import ModbusUdpClient as ModbusClient
import sys
import psycopg2
from pymodbus.client.sync import ModbusSerialClient as ModbusClient
from time import sleep
#import time
#from threading import Thread

#---------------------------------------------------------------------------# 
# configure the client logging
#---------------------------------------------------------------------------# 
import logging
logging.basicConfig()
log = logging.getLogger()
log.setLevel(logging.ERROR)

#---------------------------------------------------------------------------# 
# choose the client you want
#---------------------------------------------------------------------------# 
# make sure to start an implementation to hit against. For this
# you can use an existing device, the reference implementation in the tools
# directory, or start a pymodbus server.
#
# If you use the UDP or TCP clients, you can override the framer being used
# to use a custom implementation (say RTU over TCP). By default they use the
# socket framer::
#
#    client = ModbusClient('localhost', port=5020, framer=ModbusRtuFramer)
#
# It should be noted that you can supply an ipv4 or an ipv6 host address for
# both the UDP and TCP clients.
#
# There are also other options that can be set on the client that controls
# how transactions are performed. The current ones are:
#
# * retries - Specify how many retries to allow per transaction (default = 3)
# * retry_on_empty - Is an empty response a retry (default = False)
# * source_address - Specifies the TCP source address to bind to
#
# Here is an example of using these options::
#
#    client = ModbusClient('localhost', retries=3, retry_on_empty=True)
#---------------------------------------------------------------------------# 
#client = ModbusClient('localhost', port=502)
#client = ModbusClient(method='ascii', port='/dev/pts/2', timeout=1)
client = ModbusClient(method='rtu', port='COM5', timeout=1,stopbits = 1, bytesize = 8, parity = 'O', baudrate=9600)
client.connect()

#---------------------------------------------------------------------------# 
# specify slave to query
#---------------------------------------------------------------------------# 
# The slave to query is specified in an optional parameter for each
# individual request. This can be done by specifying the `unit` parameter
# which defaults to `0x00`
#---------------------------------------------------------------------------# 
#rr = client.read_coils(1, 1, unit=0x02)

#---------------------------------------------------------------------------# 
# example requests
#---------------------------------------------------------------------------# 
# simply call the methods that you would like to use. An example session
# is displayed below along with some assert checks. Note that some modbus
# implementations differentiate holding/input discrete/coils and as such
# you will not be able to write to these, therefore the starting values
# are not known to these tests. Furthermore, some use the same memory
# blocks for the two sets, so a change to one is a change to the other.
# Keep both of these cases in mind when testing as the following will
# _only_ pass with the supplied async modbus server (script supplied).
#---------------------------------------------------------------------------# 
#rq = client.write_coil(1, True)
var = 1
try:
    conn=psycopg2.connect("dbname='hcam' user='adminbwhvjhr' password='xnw5Px3WqUi6'")
except:
    print "I am unable to connect to the database."

cur = conn.cursor()

while var == 1 :
	rr1 = client.read_coils(2048,10,unit=0x01)
	#print rr1.bits[0]
	#print rr1.bits[1]
	#print rr1.bits[2]
	#print rr1.bits[8]

	rr2 = client.read_coils(3099,2,unit=0x01)
	#print rr2.bits[0]
	#print rr2.bits[1]

	rr3 = client.read_holding_registers(1088,2,unit=0x01)
	#print hex(rr3.registers[0]).lstrip("0x")
	#print hex(rr3.registers[1]).lstrip("0x")

	try:
		query = "insert into presion_flujo(id,fecha,presion,flujo) values(nextval('seq_presion_flujo'), current_timestamp, %s, %s)"
                presion = hex(rr3.registers[0]).lstrip("0x")
                if len(presion) == 0:
                    presion = "0"
                flujo = hex(rr3.registers[1]).lstrip("0x")
                if len(flujo) == 0:
                    flujo = "0"
		data = (presion, flujo)
		cur.execute(query, data)
		conn.commit()

		query = "insert into estado_bombas(id,fecha,bomba1,bomba2,bomba3,alarma,bajapresion,altapresion) values(nextval('seq_estado_bombas'), current_timestamp, %s, %s, %s, %s, %s, %s)"
		data = (rr1.bits[0], rr1.bits[1], rr1.bits[2], rr1.bits[8], rr2.bits[0], rr2.bits[1])
		cur.execute(query, data)
		conn.commit

	except:
	    print "I can't insert ", sys.exc_info()[0]

	#sleep(2)


#assert(rq.function_code < 0x80)     # test that we are not an error
#assert(rr.bits[0] == True)          # test the expected value

#rq = client.write_coils(1, [True]*8)
#rr = client.read_coils(3099,2)
#log.debug(rr);
#assert(rq.function_code < 0x80)     # test that we are not an error
#assert(rr.bits == [True]*8)         # test the expected value

#rq = client.write_coils(1, [False]*8)
#rr = client.read_discrete_inputs(1088,2)
#log.debug(rr);
#assert(rq.function_code < 0x80)     # test that we are not an error
#assert(rr.bits == [False]*8)         # test the expected value

#rq = client.write_register(1, 10)
#rr = client.read_holding_registers(1088,2)
#log.debug(rr);
#assert(rq.function_code < 0x80)     # test that we are not an error
#assert(rr.registers[0] == 10)       # test the expected value

#rq = client.write_registers(1, [10]*8)
#rr = client.read_input_registers(1088,2)
#log.debug(rr);
#assert(rq.function_code < 0x80)     # test that we are not an error
#assert(rr.registers == [10]*8)      # test the expected value

arguments = {
    'read_address':    1,
    'read_count':      8,
    'write_address':   1,
    'write_registers': [20]*8,
}
#rq = client.readwrite_registers(**arguments)
#rr = client.read_input_registers(1,8)
#assert(rq.function_code < 0x80)     # test that we are not an error
#assert(rq.registers == [20]*8)      # test the expected value
#assert(rr.registers == [20]*8)      # test the expected value

#---------------------------------------------------------------------------# 
# close the client
#---------------------------------------------------------------------------# 
client.close()
