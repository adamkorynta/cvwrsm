C
      SUBROUTINE ZGTPFD (IFLTAB, CPATH, NPATH, NORD, NCURVE, IHORIZ,
     * C1UNIT, C2UNIT, C1TYPE, C2TYPE, CLABEL, KLABEL, NLABEL, IBUFF,
     * KBUFF, NBUFF, VALUES, KVALS, NVALS, ISTAT)
C
C     Replaced by ZRPD
C
C
      LOGICAL LABEL
      CHARACTER *(*) CPATH,C1UNIT,C2UNIT,C1TYPE,C2TYPE,CLABEL
      DIMENSION CLABEL(*),VALUES(*)
      INTEGER IFLTAB(*)
C
C
C
      CALL ZRPD (IFLTAB, CPATH(1:NPATH), NORD, NCURVE, IHORIZ,
     * C1UNIT, C1TYPE, C2UNIT, C2TYPE, VALUES, KVALS, NVALS,
     * CLABEL, KLABEL, LABEL, IUHEAD, 0, NUHEAD, ISTAT)
C
C
      IF (ISTAT.EQ.0) THEN
      IF (LABEL) THEN
      NLABEL = NCURVE
      ELSE
      NLABEL = 0
      ENDIF
      ENDIF
C
      RETURN
      END
