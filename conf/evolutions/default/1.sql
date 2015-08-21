# Tasks schema

# --- !Ups
CREATE TABLE PROJECTS (
	id	bigint	NOT NULL AUTO_INCREMENT,
	name	varchar,
	description	varchar,
	PRIMARY KEY(id)
);
CREATE TABLE REGIONS (
       id bigint      NOT NULL AUTO_INCREMENT,
       creationTimestamp    date,
       name    varchar ,
       description    varchar ,
       status  varchar ,
       quotasJson     varchar ,
       deprecatedJson        varchar ,
       projectId      bigint ,
       PRIMARY KEY (id),
       FOREIGN KEY (projectId) REFERENCES PROJECTS(id)
);
CREATE TABLE ZONES (
       id      bigint NOT NULL AUTO_INCREMENT,
       creationTimestamp    date,
       name    varchar ,
       description    varchar ,
       status  varchar ,
       regionId       bigint ,
       projectId      bigint ,
       maintenanceWindowsJson      varchar ,
       deprecatedJson        varchar ,
       PRIMARY KEY (id),
       FOREIGN KEY (regionId) REFERENCES REGIONS(id),
       FOREIGN KEY (projectId) REFERENCES PROJECTS(id)
);


INSERT INTO PROJECTS(id, name, description) VALUES (1, 'test-vim-120', 'default test project');
INSERT INTO PROJECTS(id, name) VALUES (2, 'test-vim-126');
INSERT INTO REGIONS(id, name, creationTimestamp, status, description, quotasJson, projectId) VALUES(1, 'asia-east1', NOW(), 'UP', 'asia-east1', '[{"metric": "INSTANCE_GROUP_MANAGERS", "limit": 50, "usage": 0}, {"metric": "INSTANCES", "limit": 240, "usage": 0}]', 1);
INSERT INTO REGIONS(id, name, creationTimestamp, status, description, projectId) VALUES(2, 'asia-west1', NOW(), 'DOWN', 'asia-west1', 1);
INSERT INTO ZONES(id, name, creationTimestamp, status, description, regionId, projectId, maintenanceWindowsJson) VALUES(1, 'asia-east1-a', NOW(), 'UP', 'asia-east1-a', 1, 1, '[{"name": "monthly", "description": "monthly maintenance stategy", "beginTime": "2014-07-15T10:44:08.663-07:00", "endTime": "2014-07-15T12:44:08.663-07:00"}]');
INSERT INTO ZONES(id, name, creationTimestamp, status, description, regionId, projectId, deprecatedJson, maintenanceWindowsJson) VALUES(2, 'asia-east1-b', NOW(), 'UP', 'asia-east1-b', 1, 1, '{"state":"deprecated", "deprecated": "2014-07-15T10:44:08.663-07:00"}', '[{"name": "yearly", "description": "yearly maintenance stategy", "beginTime": "2014-07-15T10:44:08.663-07:00", "endTime": "2015-07-15T10:44:08.663-07:00"}]')

# --- !Downs
DROP TABLE IF EXISTS ZONES;
DROP TABLE IF EXISTS REGIONS;
DROP TABLE IF EXISTS PROJECTS;

