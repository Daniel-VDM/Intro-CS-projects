(define (find s predicate)
  (if (null? s) False
    (if (predicate (car s)) (car s)
      (find (cdr-stream s) predicate)
    )
  )
)

(define (scale-stream s k)
  (cons-stream (* (car s) k) (scale-stream (cdr-stream s) k))
)

(define (has-cycle s)

  (define (contains? lst value)
    (cond
      ((null? lst) False)
      ((eq? (car lst) value) True)
      (else
        (contains? (cdr lst) value)
      )
    )
  )

  ;Personal Note that the seen list that i am using here is a list / stream where the car is a stream INSTANCE
  (define (check lst seen)
    (cond
      ((null? (cdr-stream lst)) False)
      ((contains? seen lst) True)
      (else
        (check (cdr-stream lst) (cons lst seen))
      )
    )
  )

  (check s nil)
)


(define (has-cycle-constant s)
  
  (define (contains-stream? lst value)
    (cond
      ((null? lst) False)
      ((eq? (car lst) value) True)
      (else
        (contains-stream? (cdr-stream lst) value)
      )
    )
  )

  (define (check lst seen)
    (cond
      ((null? (cdr-stream lst)) False)
      ((contains-stream? seen lst) True)
      (else
        (check (cdr-stream lst) (cons-stream lst seen))
      )
    )
  )

  (check s nil)
)
