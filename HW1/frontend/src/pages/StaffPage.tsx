import { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./StaffPage.css";

export function StaffPage() {
    const [token, setToken] = useState("");
    const [feedback, setFeedback] = useState<{ type: "success" | "error", message: string } | null>(null);
    const navigate = useNavigate();

    const handleCheckIn = () => {
        if (!token.trim()) return;

        fetch(`http://localhost:8081/api/reservations/checkin/${token}`, {
            method: "POST"
        })
            .then(res => {
                if (res.ok) {
                    setFeedback({ type: "success", message: "Entrada registada com sucesso!" });
                } else {
                    return res.text().then(msg => {
                        throw new Error(msg || "Erro ao fazer check-in.");
                    });
                }
            })
            .catch(err => {
                setFeedback({ type: "error", message: err.message || "Token inválido ou já usado." });
            });
    };

    return (
        <div className="staff-page" style={{ paddingTop: "100px" }}>
            <h2>Gestão de Entradas - Staff</h2>

            <input
                type="text"
                placeholder="Insira o token da reserva"
                value={token}
                onChange={(e) => setToken(e.target.value)}
                className="token-input"
            />

            <button className="checkin-btn" onClick={handleCheckIn}>Confirmar Entrada</button>

            {feedback && (
                <div className={`modal ${feedback.type}`}>
                    <p>{feedback.message}</p>
                    <button onClick={() => {
                        setFeedback(null);
                        setToken("");
                        navigate("/restaurants");
                    }}>Fechar</button>
                </div>
            )}
        </div>
    );
}
