select c.customer_id, c.email, a.password FROM Account as a JOIN Customer as c
ON a.customer_id = c.customer_id
WHERE c.email = 'abcxyz1@gmail.com' AND a.password = '123456'