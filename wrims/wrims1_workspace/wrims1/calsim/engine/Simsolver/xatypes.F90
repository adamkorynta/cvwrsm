!     Last change:  AM    3 Dec 1999    4:56 pm
!     Copyright (C) 1998, 2000 State of California, Department of Water
!     Resources.

!     This program is licensed to you under the terms of the GNU General
!     Public License, version 2, as published by the Free Software
!     Foundation.

!     You should have received a copy of the GNU General Public License
!     along with this program; if not, contact Dr. Sushil Arora, below,
!     or the Free Software Foundation, 675 Mass Ave, Cambridge, MA
!     02139, USA.

!     THIS SOFTWARE AND DOCUMENTATION ARE PROVIDED BY THE CALIFORNIA
!     DEPARTMENT OF WATER RESOURCES AND CONTRIBUTORS "AS IS" AND ANY
!     EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
!     IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
!     PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE CALIFORNIA
!     DEPARTMENT OF WATER RESOURCES OR ITS CONTRIBUTORS BE LIABLE FOR
!     ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
!     CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT
!     OR SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA OR PROFITS; OR
!     BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
!     LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
!     (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
!     USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
!     DAMAGE.

!     For more information, contact:

!     Dr. Sushil Arora
!     California Dept. of Water Resources
!     Division of Planning, Delta Modeling Section
!     1416 Ninth Street
!     Sacramento, CA  95814
!     916-653-7921
!     sushil@water.ca.gov



!   Written by:  Armin Munevar, California Department of Water Resources

MODULE XATYPES

  IMPLICIT NONE

  INTEGER :: maxRcc,maxVars
!CB  PARAMETER(maxRcc = 150000, maxVars=15000)      ! max Rows in RCC and max variables
  PARAMETER(maxRcc = 200000, maxVars=50000) !CB      ! max Rows in RCC and max variables CalSim 3 needs it BIG!
  INTEGER(2), parameter :: rowsize = 32     ! length of row name
!CB if want 32 char dvarnames?  INTEGER(2), PARAMETER :: colsize = 32     ! length of column name
  INTEGER(2), PARAMETER :: colsize = 32     ! length of column name

  ! Two derived types:  one for the problem description and the other for the solution
  TYPE rcc
     CHARACTER(LEN=32)  :: rowname
!CB if want 32 char dvarnames?       CHARACTER(LEN=32)  :: colname
     CHARACTER(LEN=32)  :: colname
     REAL(8)            :: coef
  END TYPE rcc

  TYPE answer
     CHARACTER(LEN=colsize) id
     REAL(8) value
  END type answer

END MODULE XATYPES
