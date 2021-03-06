--调度任务
CREATE TABLE JOBTASK (
    ID INT NOT NULL AUTO_INCREMENT,
    BEAN_ID VARCHAR(30) NOT NULL,
    METHOD_NAME VARCHAR(30) NOT NULL,
    `GROUP` VARCHAR(20),
    EXPRESSION VARCHAR(30),
    DESCRIPTION VARCHAR(50),
    STATUS INT,
    CREATETIME TIMESTAMP NOT NULL,
    LASTUPDATE TIMESTAMP NOT NULL,
    PARAMS VARCHAR(100),
    PRIMARY KEY (ID),
    UNIQUE KEY BEANID_METHODNAME (BEAN_ID, METHOD_NAME)
);

--调度执行日志
CREATE TABLE JOB_EXECUTION_LOG (
    ID INT NOT NULL AUTO_INCREMENT,
    JOBTASK_ID INT NOT NULL,
    STATUS INT NOT NULL,
    STARTTIME TIMESTAMP NOT NULL,
    FINISHEDTIME TIMESTAMP,
    CONTEXT VARCHAR(255),
    PRIMARY KEY (ID),
    FOREIGN KEY FK_JOB_EXECUTION_LOG_ID (JOBTASK_ID) REFERENCES JOBTASK(ID) ON DELETE CASCADE
);