      SUBROUTINE ZSPDD (IFLTAB, CPATH, NORD, NCURVE, IHORIZ,
     * C1UNIT, C1TYPE, C2UNIT, C2TYPE, DVALUES, CLABEL, LABEL,
     * IUHEAD, NUHEAD, IPLAN, ISTAT)
C
C     Store Paired DATA
C
C     Written by Bill Charley
C
C
      INTEGER IFLTAB(*), IUHEAD(*)
      CHARACTER CPATH*(*), CLABEL(*)*(*)
      CHARACTER C1UNIT*(*), C1TYPE*(*), C2UNIT*(*), C2TYPE*(*)
      REAL SVALUES(1)
      LOGICAL LABEL
      DOUBLE PRECISION DVALUES(*)
C
C
      CALL ZSPDI (IFLTAB, CPATH, NORD, NCURVE, IHORIZ,
     * C1UNIT, C1TYPE, C2UNIT, C2TYPE, SVALUES, DVALUES,
     * .TRUE., CLABEL, LABEL, IUHEAD, NUHEAD, IPLAN, ISTAT)
C
      END
