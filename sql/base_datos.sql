-- Database: hcam

-- DROP DATABASE hcam;

CREATE DATABASE hcam
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'Spanish_Ecuador.1252'
       LC_CTYPE = 'Spanish_Ecuador.1252'
       CONNECTION LIMIT = -1;


-- Table: movimiento_diesel

-- DROP TABLE movimiento_diesel;

CREATE TABLE movimiento_diesel
(
  id numeric(10,0) NOT NULL,
  fecha timestamp without time zone NOT NULL,
  alarma character varying(10) NULL,
  descarga numeric(10,2) NULL,
  salida numeric(10,2) NULL,
  temperatura numeric(10,2) NULL,
  acumulado_tanque1 numeric(10,2) NULL,
  acumulado_tanque2 numeric(10,2) NULL,
  CONSTRAINT movimiento_pk PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE movimiento_diesel
  OWNER TO postgres;
  
-- Table: estado_bombas_diesel

-- DROP TABLE estado_bombas_diesel;

CREATE TABLE estado_bombas_diesel
(
  id numeric(10,0) NOT NULL,
  fecha timestamp without time zone NOT NULL,
  bomba1 boolean NOT NULL,
  bomba2 boolean NOT NULL,
  CONSTRAINT estado_bombas_diesel_pk PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE estado_bombas_diesel
  OWNER TO postgres;
  
-- Table: estado_bombas

-- DROP TABLE estado_bombas;

CREATE TABLE estado_bombas
(
  id numeric(10,0) NOT NULL,
  fecha timestamp without time zone NOT NULL,
  bomba1 boolean NOT NULL,
  bomba2 boolean NOT NULL,
  bomba3 boolean NOT NULL,
  alarma boolean NOT NULL,
  bajapresion boolean NOT NULL DEFAULT false,
  altapresion boolean NOT NULL DEFAULT false,
  CONSTRAINT estado_bombas_pk PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE estado_bombas
  OWNER TO postgres;

  
-- Table: presion_flujo

-- DROP TABLE presion_flujo;

CREATE TABLE presion_flujo
(
  id numeric(10,0) NOT NULL,
  fecha timestamp without time zone NOT NULL,
  presion character varying(10) NOT NULL,
  flujo character varying(10) NOT NULL,
  consolidado boolean DEFAULT false,
  CONSTRAINT presion_flujo_pk PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE presion_flujo
  OWNER TO postgres;

-- Table: usuario

-- DROP TABLE usuario;

CREATE TABLE usuario
(
  id numeric(10,0) NOT NULL,
  nombre character varying(10) NOT NULL,
  clave character varying(10) NOT NULL,
  CONSTRAINT usuario_pk PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE presion_flujo
  OWNER TO postgres;
 
-- Table: vapor

-- DROP TABLE vapor;

CREATE TABLE vapor
(
  id numeric(10,0) NOT NULL,
  fecha timestamp without time zone NOT NULL,
  valvula character varying(200) NOT NULL,
  estado character varying(10) NOT NULL,
  flujo numeric(10,2) NOT NULL,
  presion numeric(10,2) NOT NULL,
  temperatura numeric(10,2) NOT NULL,
  CONSTRAINT vapor_pk PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE presion_flujo
  OWNER TO postgres;

-- Table: consumo_agua

-- DROP TABLE consumo_agua;

CREATE TABLE consumo_agua
(
  id numeric(10,0) NOT NULL,
  fecha timestamp without time zone NOT NULL,
  consumo numeric(10,2) NOT NULL,
  CONSTRAINT consumo_agua_pk PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE consumo_agua
  OWNER TO postgres;

-- Table: agua

-- DROP TABLE agua;

CREATE TABLE agua
(
  id numeric(10,0) NOT NULL,
  fecha timestamp without time zone NOT NULL,
  presion numeric(10,2) NOT NULL,
  flujo numeric(10,2) NOT NULL,
  bomba_1 character varying(12) NOT NULL,
  bomba_2 character varying(12) NOT NULL,
  bomba_3 character varying(12) NOT NULL,
  alarma character varying(10) NOT NULL,
  consolidado boolean DEFAULT false,
  CONSTRAINT agua_pk PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE agua
  OWNER TO postgres;

-- Table: consumo_vapor

-- DROP TABLE consumo_vapor;

CREATE TABLE consumo_vapor
(
  id numeric(10,0) NOT NULL,
  fecha timestamp without time zone NOT NULL,
  consumo_total numeric(10,2) NOT NULL,
  consumo_cb1 numeric(10,2) NOT NULL,
  consumo_tanques_agua_caliente numeric(10,2) NOT NULL,
  consumo_cocina numeric(10,2) NOT NULL,
  consumo_patologia_laboratorio_central numeric(10,2) NOT NULL,
  consumo_obstetricia numeric(10,2) NOT NULL,
  consumo_biberones numeric(10,2) NOT NULL,
  consumo_piscina numeric(10,2) NOT NULL,
  consumo_quirofano_sala_partos numeric(10,2) NOT NULL,
  consumo_lavanderia_comedor numeric(10,2) NOT NULL,
  consumo_lavanderia numeric(10,2) NOT NULL,
  consumo_cb2_cb4 numeric(10,2) NOT NULL,
  consumo_cb3_cb5 numeric(10,2) NOT NULL,
  consumo_quemados numeric(10,2) NOT NULL,
  CONSTRAINT consumo_vapor_pk PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE consumo_vapor
  OWNER TO postgres;

-- Sequence: seq_consumo_vapor

-- DROP SEQUENCE seq_consumo_vapor;

CREATE SEQUENCE seq_consumo_vapor
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE seq_consumo_vapor
  OWNER TO postgres;


-- Sequence: seq_agua

-- DROP SEQUENCE seq_agua;

CREATE SEQUENCE seq_agua
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE seq_agua
  OWNER TO postgres;




-- Sequence: seq_movimiento

-- DROP SEQUENCE seq_movimiento;

CREATE SEQUENCE seq_movimiento
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE seq_movimiento
  OWNER TO postgres;
  
-- Sequence: seq_estado_bombas

-- DROP SEQUENCE seq_estado_bombas;

CREATE SEQUENCE seq_estado_bombas
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE seq_estado_bombas
  OWNER TO postgres;
  
-- Sequence: seq_presion_flujo

-- DROP SEQUENCE seq_presion_flujo;

CREATE SEQUENCE seq_presion_flujo
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE seq_presion_flujo
  OWNER TO postgres;
  
-- Sequence: seq_estado_bombas_diesel

-- DROP SEQUENCE seq_estado_bombas_diesel;

CREATE SEQUENCE seq_estado_bombas_diesel
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE seq_estado_bombas_diesel
  OWNER TO postgres;


-- Sequence: seq_consumo_agua

-- DROP SEQUENCE seq_consumo_agua;

CREATE SEQUENCE seq_consumo_agua
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE seq_consumo_agua
  OWNER TO postgres;

-- Sequence: seq_consumo_mes_agua

-- DROP SEQUENCE seq_consumo_mes_agua;

CREATE SEQUENCE seq_consumo_mes_agua
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 44
  CACHE 1;
ALTER TABLE seq_consumo_mes_agua
  OWNER TO postgres;
  
  
-- Sequence: seq_vapor

-- DROP SEQUENCE seq_vapor;

CREATE SEQUENCE seq_vapor
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE seq_vapor
  OWNER TO postgres;
  
alter table consumo_agua add column consolidado boolean DEFAULT false;

-- Table: consumo_mes_agua

-- DROP TABLE consumo_mes_agua;

CREATE TABLE consumo_mes_agua
(
  id numeric(10,0) NOT NULL,
  fecha timestamp without time zone NOT NULL,
  consumo_total_mes numeric(10,2) NOT NULL,
  CONSTRAINT consumo_mes_agua_pk PRIMARY KEY (id)
);

CREATE TABLE datos_plc_diesel
(
  id numeric(10,0) NOT NULL,
  fecha timestamp without time zone NOT NULL,
  temperatura integer NOT NULL,
  bajo_tanque1 integer NOT NULL,
  alto_tanque1 integer NOT NULL,
  bajo_tanque2 integer NOT NULL,
  alto_tanque2 integer NOT NULL,
  pulsos_entrada numeric(10,0) NOT NULL,
  bomba_ingreso integer NOT NULL,
  frecuencia_entrada integer NOT NULL,
  bomba_tdiario integer NOT NULL,
  galones_salida integer NOT NULL,
  fracc_galonsalida integer NOT NULL,
  frecuencia_salida integer NOT NULL,
  flujo_salida integer NOT NULL,
  fracc_flujosalida integer NOT NULL,
  galones_entrada integer NOT NULL,
  fracc_galonentrada integer NOT NULL,
  flujo_entrada integer NOT NULL,
  fracc_flujoentrada integer NOT NULL,
  paro_emergencia integer NOT NULL,
  inicio_galont1 integer NOT NULL,
  inicio_fraccgalont1 integer NOT NULL,
  inicio_galont2 integer NOT NULL,
  inicio_fraccgalont2 integer NOT NULL,
  total_galont1 integer NOT NULL,
  total_fraccgalont1 integer NOT NULL,
  total_galont2 integer NOT NULL,
  total_fraccgalont2 integer NOT NULL,
  pedido_tanque integer NOT NULL,
  tanque_uso integer NOT NULL,
  modo integer NOT NULL DEFAULT 1,
  reset_ingreso integer DEFAULT 20,
  consumo integer NOT NULL DEFAULT 0,
  fracc_consumo integer NOT NULL DEFAULT 0,
  CONSTRAINT datos_modbus_diesel_pk PRIMARY KEY (id)
);

-- Sequence: seq_datos_plc_diesel

-- DROP SEQUENCE seq_datos_plc_diesel;

CREATE SEQUENCE seq_datos_plc_diesel
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

  
alter table agua add column migrado boolean DEFAULT false;
alter table consumo_agua add column migrado boolean DEFAULT false;
alter table consumo_mes_agua add column migrado boolean DEFAULT false;

alter table estado_bombas add column consolidado boolean DEFAULT false;
  
CREATE TABLE consumo_mes_diesel
(
  id numeric(10,0) NOT NULL,
  fecha timestamp without time zone NOT NULL,
  consumo_total_mes numeric(10,2) NOT NULL,
  migrado boolean DEFAULT false,
  CONSTRAINT consumo_mes_diesel_pk PRIMARY KEY (id)
);

CREATE SEQUENCE seq_consumo_mes_diesel
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
  
  -- Table: consumo_diesel

-- DROP TABLE consumo_diesel;

CREATE TABLE consumo_diesel
(
  id numeric(10,0) NOT NULL,
  fecha timestamp without time zone NOT NULL,
  consumo numeric(10,2) NOT NULL,
  migrado boolean NOT NULL DEFAULT false,
  CONSTRAINT consumo_diesel_pk PRIMARY KEY (id)
);
  
 -- Sequence: seq_consumo_diesel

-- DROP SEQUENCE seq_consumo_diesel;

CREATE SEQUENCE seq_consumo_diesel
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

  
alter table vapor add column alarma_alta_presion boolean DEFAULT false;
alter table vapor add column alarma_baja_presion boolean DEFAULT false;

alter table datos_plc_diesel add column migrado boolean DEFAULT false;