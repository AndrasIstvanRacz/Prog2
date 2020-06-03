(define (factorial szam)
(if (< szam 2)
1
(* szam (factorial ( - szam 1))))
)
(princ "Szam: ")
(setq z (read))
(princ (factorial z))
