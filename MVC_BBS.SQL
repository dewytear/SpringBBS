    CREATE  TABLE MVC_BBS
    (
		bNO_BBS			NUMBER(5,0)			PRIMARY KEY,

		bNM_BBS			VARCHAR2(50)		,
		bDT_BBS			DATE 				DEFAULT SYSDATE,
		bSUBJECT		VARCHAR2(80)		,
		bCONTENT		VARCHAR2(2000)		,
		bHIT			NUMBER(5,0)			DEFAULT 0,
        bGROUP			NUMBER(5,0)			,
        bSTEP			NUMBER(5,0)			,
        bINDENT			NUMBER(5,0)			,

		DTS_INSERT		VARCHAR2(14)		,
		ID_INSERT		VARCHAR2(15)		,
		DTS_UPDATE		VARCHAR2(14)		,
		ID_UPDATE		VARCHAR2(15)		

    );
    
    create sequence seq_bbs;
    
    insert into MVC_BBS(
    					bNO_BBS		
                        , bNM_BBS		
                        , bSUBJECT	
                        , bCONTENT	
                        , bHIT		
                        , bGROUP		
                        , bSTEP		
                        , bINDENT	
                        )
	values(
    		seq_bbs.nextval
    		, 'test'
            , 'test �Դϴ�.'
            , 'Hello BBS!!'
            , 0
            , seq_bbs.currval
            , 0
            , 0
    );

	select * from MVC_BBS;
	
	GRANT SELECT, INSERT, UPDATE, DELETE ON SYSTEM.MVC_BBS TO scott;

	create OR REPLACE public synonym MVC_BBS for SYSTEM.MVC_BBS;
	
	-- create synonym
	CREATE OR REPLACE public SYNONYM seq_bbs FOR system.seq_bbs;
	-- set grant
	GRANT ALTER, SELECT ON system.seq_bbs TO scott;

