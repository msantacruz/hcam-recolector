#!/usr/bin/env python
import sys
import psycopg2

try:
    conn=psycopg2.connect("dbname='hcam' user='adminbwhvjhr' password='xnw5Px3WqUi6'")
except:
    print "I am unable to connect to the database."

cur = conn.cursor()

try:
	#query = "insert into presion_flujo(id,fecha,presion,flujo) values(nextval('seq_presion_flujo'), current_timestamp, '100', '200')"
	#data = (hex(rr3.registers[0]).lstrip("0x"), hex(rr3.registers[1]).lstrip("0x"))
	cur.execute("""insert into presion_flujo(id,fecha,presion,flujo) values(nextval('seq_presion_flujo'), current_timestamp, '100', '200')""")
	#cur.execute("""select * from presion_flujo""")
	#cur.commit()

	#query = "insert into estado_bombas(id,fecha,bomba1,bomba2,bomba3,alarma,bajapresion,altapresion) values(nextval('seq_estado_bombas'), current_timestamp, %s, %s, %s, %s, %s, %s)"
	#data = (rr1.bits[0], rr1.bits[1], rr1.bits[2], rr1.bits[8], rr2.bits[0], rr2.bits[1])
	#cur.execute(query, data)
	#cur.commit

except:
    print "I can't insert ", sys.exc_info()[0]