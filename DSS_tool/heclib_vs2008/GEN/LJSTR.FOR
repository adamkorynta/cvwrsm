      SUBROUTINE LJSTR (CSTR1,NBEG1,NLEN1,CSTR2,NBEG2)
C
C     LEFT JUSTIFYS A STRING (CSTR1) INTO CSTR2
C
      CHARACTER CSTR1*(*), CSTR2*(*)
      COMMON /JSTRS/ CTMP
      CHARACTER CTMP*200
C
      CALL LFLNB (CSTR1,NBEG1,NLEN1,I,J)
      IF (I.GT.0.AND.J.GT.0) THEN
      CTMP = CSTR1
      CSTR2(NBEG2:J-1+NBEG2) = CTMP(I:J+I-1)
      IF (J.LT.NLEN1) THEN
      CSTR2(J+NBEG2:NBEG2+NLEN1-1) = ' '
      ENDIF
      ELSE
      CSTR2(NBEG2:NBEG2+NLEN1-1) = ' '
      ENDIF
      RETURN
      END
