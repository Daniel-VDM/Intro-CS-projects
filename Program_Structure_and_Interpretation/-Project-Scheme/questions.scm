(define (caar x) (car (car x)))
(define (cadr x) (car (cdr x)))
(define (cdar x) (cdr (car x)))
(define (cddr x) (cdr (cdr x)))

; Some utility functions that you may find useful to implement.

(define (cons-all first rests)
  (map (lambda (x) (cons first x)) rests))

(define (zip pairs)
  (cons (map car pairs) (list (map cadr pairs))))

;; Problem 17
;; Returns a list of two-element lists
(define (enumerate s)
  (define (enum i lst)
    (if (null? lst)
      nil
      (cons (list i (car lst))
            (enum (+ i 1) (cdr lst)))))
  (enum 0 s))

;; Problem 18
;; List all ways to make change for TOTAL with DENOMS
(define (list-change total denoms)
  (cond 
    ((= total 0) (cons nil nil))
    ((or (< total 0) (null? denoms)) nil)
    (else
      (let ((with-first (cons-all (car denoms) (list-change (- total (car denoms)) denoms)) )
            (without-first (list-change total (cdr denoms))))
        (append with-first without-first)))))


;; Problem 19
;; Returns a function that checks if an expression is the special form FORM
(define (check-special form)
  (lambda (expr) (equal? form (car expr))))

(define lambda? (check-special 'lambda))
(define define? (check-special 'define))
(define quoted? (check-special 'quote))
(define let?    (check-special 'let))

;; Converts all let special forms in EXPR into equivalent forms using lambda
(define (let-to-lambda expr)
  (cond ((atom? expr)
         ; BEGIN PROBLEM 19
         expr
         ; END PROBLEM 19
         )
        ((quoted? expr)
         ; BEGIN PROBLEM 19
         expr
         ; END PROBLEM 19
         )
        ((or (lambda? expr)
             (define? expr))
         (let ((form   (car expr))
               (params (cadr expr))
               (body   (cddr expr)))
           ; BEGIN PROBLEM 19
           (cons form (cons params (let-to-lambda body)))
           ; END PROBLEM 19
           ))
        ((let? expr)
         (let ((values (cadr expr))
               (body   (cddr expr)))
           ; BEGIN PROBLEM 19
           (cons 
             (cons 
              (quote lambda) 
              (cons 
                (car (zip (let-to-lambda values))) 
                (let-to-lambda body)
              )
             ) 
             (cadr (zip (let-to-lambda values)))
           )
           ; END PROBLEM 19
           ))
        (else
         ; BEGIN PROBLEM 19
         (map let-to-lambda expr)
         ; END PROBLEM 19
         )))
